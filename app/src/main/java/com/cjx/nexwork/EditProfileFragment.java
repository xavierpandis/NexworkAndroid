package com.cjx.nexwork;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cjx.nexwork.activities.MainActivity;
import com.cjx.nexwork.managers.TokenStoreManager;
import com.cjx.nexwork.managers.UploadImageCallback;
import com.cjx.nexwork.managers.user.UserDetailCallback;
import com.cjx.nexwork.managers.user.UserManager;
import com.cjx.nexwork.model.User;
import com.cjx.nexwork.util.CustomProperties;
import com.cjx.nexwork.util.FileUtils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */

public class EditProfileFragment extends Fragment implements UserDetailCallback, UploadImageCallback, View.OnClickListener {

    private int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 1;

    private static final int DEFAULT_MIN_WIDTH_QUALITY = 400;        // min pixels
    private static final String TAG = "ImagePicker";
    private static final String TEMP_IMAGE_NAME = "tempImage";

    ImageView userimage;
    EditText firstname;
    EditText lastname;
    EditText email;
    EditText userdate;
    EditText github;
    EditText facebook;
    EditText twitter;
    EditText webpage;
    EditText skype;
    EditText phone;
    EditText useraddress;
    EditText city;
    EditText presentationletter;
    Button saveDataUser;

    User user;

    public EditProfileFragment() {
    }


    public static int minWidthQuality = DEFAULT_MIN_WIDTH_QUALITY;

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(getContext(), "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.edit_profile, container, false);

        userimage = (ImageView) view.findViewById(R.id.imageButton);

        firstname = (EditText) view.findViewById(R.id.firstName);
        lastname = (EditText) view.findViewById(R.id.lastName);
        email = (EditText) view.findViewById(R.id.userEmail);
        userdate = (EditText) view.findViewById(R.id.userDate);
        github = (EditText) view.findViewById(R.id.userGithub);
        facebook = (EditText) view.findViewById(R.id.userFacebook);
        twitter = (EditText) view.findViewById(R.id.userTwitter);
        webpage = (EditText) view.findViewById(R.id.userWebpage);
        skype = (EditText) view.findViewById(R.id.userSkype);
        phone = (EditText) view.findViewById(R.id.phoneNumber);
        useraddress = (EditText) view.findViewById(R.id.userAddress);
        city = (EditText) view.findViewById(R.id.userCity);
        presentationletter = (EditText) view.findViewById(R.id.presentationLetter);
        saveDataUser = (Button) view.findViewById(R.id.saveButton);

        UserManager.getInstance().getCurrentUser(this);


        return view;

    }

    @Override
    public void onSuccess(User user) {
        Picasso.with(getActivity())
                .load(CustomProperties.baseUrl+"/"+user.getImagen()+"?time="+System.currentTimeMillis())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(userimage);

        this.user = user;

        userimage.setOnClickListener(this);

        saveDataUser.setOnClickListener(this);

        if(user.getFirstName()!=null){
            firstname.setText(user.getFirstName());
        }
        else{
            firstname.setText("");
        }
        if(user.getLastName()!=null){
            lastname.setText(user.getLastName());
        }
        else{
            lastname.setText("");
        }
        if(user.getUserDate()!=null){
            userdate.setText(user.getUserDate().toString());
        }
        else{
            userdate.setText("");
        }
        if(user.getEmail()!=null){
            email.setText(user.getEmail());
        }
        else{
            email.setText("");
        }
        if(user.getGithub()!=null){
            github.setText(user.getGithub());
        }
        else{
            github.setText("");
        }
        if(user.getFacebook()!=null){
            facebook.setText(user.getFacebook());
        }
        else{
            facebook.setText("");
        }
        if(user.getTwitter()!=null){
            twitter.setText(user.getTwitter());
        }
        else{
            twitter.setText("");
        }
        if(user.getWeb_personal()!=null){
            webpage.setText(user.getWeb_personal());
        }
        else{
            webpage.setText("");
        }
        if(user.getSkype()!=null){
            skype.setText(user.getSkype());
        }
        else{
            skype.setText("");
        }
        if(user.getCiudad()!=null){
            city.setText(user.getCiudad());
        }
        else{
            city.setText("");
        }
        if(user.getCarta_presentacion()!=null){
            presentationletter.setText(user.getCarta_presentacion());
        }
        else{
            presentationletter.setText("");
        }
    }

    @Override
    public void onFailure(Throwable t) {

    }

    @Override
    public void onSuccessSaved(User user) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFailureSaved(Throwable t) {

    }

    public void selectImageAndroid(){
        Intent intent = new Intent();
        //Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //startActivityForResult(pickPhotoIntent, PICK_IMAGE_REQUEST);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                uploadFile(uri);
                //ImageView imageView = (ImageView) findViewById(R.id.imageView);
                //imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadFile(Uri fileUri) {

        File file = FileUtils.getFile(getActivity(), fileUri);

        String filename = file.getName();
        String filenameArray[] = filename.split("\\.");
        String extension = filenameArray[filenameArray.length-1];

        RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), TokenStoreManager.getInstance().getUsername()+"."+extension);

        UserManager.getInstance().updateUserImage(fbody, name, this);


    }

    @Override
    public void onSuccessUploadImage(String image) {
        Log.d("UserImage",image);
        if(image != null) user.setImagen(image);

        Picasso.with(getActivity())
                .load(CustomProperties.baseUrl+"/"+user.getImagen()+"?time="+System.currentTimeMillis())
                .into(userimage);
    }

    @Override
    public void onFailureUploadImage(Throwable t) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.saveButton:
            saveUserData();
                break;
            case R.id.imageButton:
                if (Build.VERSION.SDK_INT >= 23)
                {
                    if (checkPermission()) {
                        selectImageAndroid();

                    } else {
                        requestPermission(); // Code for permission
                        selectImageAndroid();
                    }
                }
                else
                {
                    selectImageAndroid();
                }
                break;
        }
    }

    public void saveUserData(){

        if(!firstname.getText().toString().equals("")){
            user.setFirstName(firstname.getText().toString());
        }
        if(!lastname.getText().toString().equals("")){
            user.setLastName(lastname.getText().toString());
        }
        if(!email.getText().toString().equals("")){
            user.setEmail(email.getText().toString());
        }
        if(!github.getText().toString().equals("")){
            user.setGithub(github.getText().toString());
        }
        if(!facebook.getText().toString().equals("")){
            user.setFacebook(facebook.getText().toString());
        }
        if(!twitter.getText().toString().equals("")){
            user.setTwitter(twitter.getText().toString());
        }
        if(!webpage.getText().toString().equals("")){
            user.setWeb_personal(webpage.getText().toString());
        }
        if(!skype.getText().toString().equals("")){
            user.setSkype(skype.getText().toString());
        }
        if(!city.getText().toString().equals("")){
            //user.setCiudad(city.getText().toString());
        }
        if(!presentationletter.getText().toString().equals("")){
            user.setCarta_presentacion(presentationletter.getText().toString());
        }

        UserManager.getInstance().updateUserData(user, this);

    }

}
