package com.cs426.imageetranslation.tabfragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.cs426.imageetranslation.R;
import com.cs426.imageetranslation.activity.translation.TranslationTabsActivity;
import com.cs426.imageetranslation.helper.GlobalState;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
//import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
//import com.google.firebase.ml.naturallanguage.languageid.FirebaseLanguageIdentification;
//import com.google.firebase.ml.naturallanguage.languageid.FirebaseLanguageIdentificationOptions;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.mlkit.nl.languageid.LanguageIdentification;
import com.google.mlkit.nl.languageid.LanguageIdentifier;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CameraFragment extends Fragment implements View.OnClickListener {
    ImageView selectedImage;
    Button btnCamera,btnGallery,btnChooseLanguage;
    Bitmap imageBitmap;
    TextView detectedText;
    public int check = 0;

    private String fullText = "";

    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;
    public CameraFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        View rootView = inflater.inflate(R.layout.acitvity_get_image, container, false);
        FirebaseApp.initializeApp(getActivity());

        return rootView;



    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectedImage = (ImageView) getView().findViewById(R.id.selectedImage);
        btnCamera = (Button) getView().findViewById(R.id.btnCamera);
        btnGallery = (Button) getView().findViewById(R.id.btnGallery);
        btnChooseLanguage = (Button) getView().findViewById(R.id.btnChooseLanguage);
        detectedText = (TextView) getView().findViewById(R.id.detectedText);
        btnChooseLanguage.setEnabled(false);
        btnCamera.setOnClickListener(this);
        btnGallery.setOnClickListener(this);
        btnChooseLanguage.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCamera:{
                askCameraPermissions();
                break;
            }
            case R.id.btnGallery:{
                Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery,GALLERY_REQUEST_CODE);
                break;
            }
            case R.id.btnChooseLanguage:{
                if(check != 1){
                    Toast.makeText(getActivity(),"Please select an image and wait for the detection!",Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(getActivity(), TranslationTabsActivity.class));
                    break;
                }
            }
        }

    }



    private void askCameraPermissions() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }
        else{
            openCamera();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(getContext(), "Camera Permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    Uri photoURI;
    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }

    }
    private String getFileExt(Uri contentUri) {
        ContentResolver c = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }

    public Uri contentUri;
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }
    // Get image from camera
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {

        if(requestCode == CAMERA_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK) {
                File f = new File(currentPhotoPath);
                selectedImage.setImageURI(Uri.fromFile(f));
                galleryAddPic();
                    try {
                        detectTextFromImage(Uri.fromFile(f));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            }
        }

        else if(requestCode == GALLERY_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                Uri contentUri = data.getData();
//                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//                String imageFileName = "JPEG_" + timeStamp + "." + getFileExt(contentUri);
                selectedImage.setImageURI(contentUri);
                try {
                    detectTextFromImage(contentUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    // Detect text from image
    private void detectTextFromImage(Uri imageURI) throws IOException {
        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromFilePath(getActivity(), imageURI);
//        FirebaseVisionCloudTextRecognizerOptions options = new FirebaseVisionCloudTextRecognizerOptions.Builder()
//                .setLanguageHints(Arrays.asList("en", "hi"))
//                .build();
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        detector.processImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                Log.d("abc", String.valueOf(firebaseVisionText));
                displayTextFromImage(firebaseVisionText);
            }
        }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("Error: ", e.getMessage());
            }
        });

    }
    // Show text to activity
    private void displayTextFromImage(FirebaseVisionText firebaseVisionText) {
        List<FirebaseVisionText.TextBlock> blockList = firebaseVisionText.getTextBlocks();
        if(blockList.size() == 0) {
            Toast.makeText(getContext(), "No Text Found In Image.", Toast.LENGTH_SHORT).show();
        }else {
            detectedText.setText("");
            for (FirebaseVisionText.TextBlock block : firebaseVisionText.getTextBlocks()) {
                String text = block.getText();
                fullText += text;
                fullText += "\n";
                detectedText.append(text);
                detectedText.append("\n");
            }
            GlobalState.fullText = fullText;
        }
        check = 1;

        identifyLanguage();

    }
    private void identifyLanguage() {
        LanguageIdentifier languageIdentifier =
                LanguageIdentification.getClient();
        languageIdentifier.identifyLanguage(fullText)
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@Nullable String languageCode) {
                                if (languageCode != "und") {
                                    GlobalState.selectedFrom = GlobalState.toBCP14(languageCode);
                                    btnChooseLanguage.setEnabled(true);
                                } else {
                                    Toast.makeText(getActivity(), "Cannot identify current language" ,Toast.LENGTH_SHORT);
                                }
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be loaded or other internal error.
                                Toast.makeText(getActivity(), "Fail to download identifying models" ,Toast.LENGTH_SHORT);
                            }
                        });
    }





}

