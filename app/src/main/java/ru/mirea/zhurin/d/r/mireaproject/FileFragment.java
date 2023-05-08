package ru.mirea.zhurin.d.r.mireaproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.File;

public class FileFragment extends Fragment {
    private static final int REQUEST_CODE_OPEN_FILE = 1;
    private static final int REQUEST_CODE_SAVE_FILE = 2;
    private static final String[] SUPPORTED_FORMATS = {".txt", ".pdf", ".docx", ".xlsx"};

    private TextView textView;
    private FloatingActionButton fab;

    private Uri fileUri;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file, container, false);

        textView = view.findViewById(R.id.text_view);
        fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        return view;
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Выберите действие")
                .setItems(new CharSequence[]{"Открыть файл", "Сохранить файл"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                openFile();
                                break;
                            case 1:
                                saveFile();
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    private void openFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        startActivityForResult(intent, REQUEST_CODE_OPEN_FILE);
    }

    private void saveFile() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.setType("*/*");
        String fileName = "new_file.txt";
        intent.putExtra(Intent.EXTRA_TITLE, fileName);
        startActivityForResult(intent, REQUEST_CODE_SAVE_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == REQUEST_CODE_OPEN_FILE) {
                if (data != null) {
                    fileUri = data.getData();
                    textView.setText(fileUri.toString());
                }
            } else if (requestCode == REQUEST_CODE_SAVE_FILE) {
                if (data != null) {
                    fileUri = data.getData();
                    String fileName = getFileNameFromUri(fileUri);
                    String fileExtension = getFileExtension(fileName);
                    if (isFormatSupported(fileExtension)) {
                        textView.setText(fileUri.toString());
                    } else {
                        Toast.makeText(getActivity(), "Неверный формат файла", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private String getFileNameFromUri(Uri uri) {
        String fileName = null;
        String[] projection = {MediaStore.MediaColumns.DISPLAY_NAME};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);
            cursor.moveToFirst();
            fileName = cursor.getString(columnIndex);
            cursor.close();
        }
        return fileName;
    }
    private String getFileExtension(String fileName) {
        String extension = null;
        int index = fileName.lastIndexOf('.');
        if (index > 0 && index < fileName.length() - 1) {
            extension = fileName.substring(index);
        }
        return extension;
    }

    private boolean isFormatSupported(String fileExtension) {
        if (fileExtension != null) {
            for (String supportedFormat : SUPPORTED_FORMATS) {
                if (fileExtension.equals(supportedFormat)) {
                    return true;
                }
            }
        }
        return false;
    }
}