package ru.mirea.zhurin.d.r.mireaproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.io.IOException;

public class MicFragment extends Fragment {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String fileName;

    private MediaRecorder recorder;
    private View visualizerView;
    private Button recordButton;

    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mic, container, false);
        visualizerView = view.findViewById(R.id.visualizer);
        recordButton = view.findViewById(R.id.record_button);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecord(!isRecording());
            }
        });

        ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_RECORD_AUDIO_PERMISSION);
        fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiorecordtest.3gp";
        return view;
    }

    private boolean isRecording() {
        return recorder != null;
    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        recorder.start();
        visualizerView.start();
        recordButton.setText(R.string.stop_recording);
    }

    private void stopRecording() {
        if (recorder != null) {
            recorder.stop();
            recorder.reset();
            recorder.release();
            recorder = null;
            visualizerView.stop();
            recordButton.setText(R.string.start_recording);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted) getActivity().finish();
    }

    private static class VisualizerView {
        public VisualizerView() {
            // Constructor code
        }

        public void start() {
            // Получаем экземпляр класса Visualizer для визуализации звука
            Visualizer visualizer = new Visualizer(0);
            // Устанавливаем канал записи звука
            visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
            visualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
                // Обработчик данных, получаемых от Visualizer
                @Override
                public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
                    // Получаем данные формы волны и частоты дискретизации
                    // Обновляем изображение для визуализации формы волны звука
                }

                @Override
                public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
                    // Получаем данные FFT и частоту дискретизации
                    // Обновляем изображение для визуализации частотного спектра звука
                }
            }, Visualizer.getMaxCaptureRate() / 2, true, false);
            visualizer.setEnabled(true);
        }

        public void stop() {
            // Отключаем Visualizer
            fileName.setEnabled(false);
            // Освобождаем ресурсы
            fileName.release();
            fileName = null;
        }
    }
}
