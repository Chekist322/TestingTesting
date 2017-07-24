package test.criminalintent;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import static android.Manifest.permission.CAMERA;

/**
 * Created by Phoen on 21.07.2017.
 */

public class CrimeFragment extends Fragment {
    private Crime mCrime;
    private Button mButton;
    private ImageView mImageView;
    private final int CAMERA_RESULT = 1;
    private Activity activity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
        activity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        mImageView = v.findViewById(R.id.imageView);
        mButton = (Button) v.findViewById(R.id.button2);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(activity, CAMERA) == PackageManager.PERMISSION_GRANTED){
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_RESULT);
                } else {
                    requestMultiplePermissions();

                }
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CAMERA_RESULT:
                Bitmap thumbnailBitmap = (Bitmap) data.getExtras().get("data");
                mImageView.setImageBitmap(thumbnailBitmap);
                break;
        }
    }

    public void requestMultiplePermissions() {
        ActivityCompat.requestPermissions(activity,
                new String[] {
                        CAMERA
                },
                CAMERA_RESULT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_RESULT){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(activity, "Good comrade!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
