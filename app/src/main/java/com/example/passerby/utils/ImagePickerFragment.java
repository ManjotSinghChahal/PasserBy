package com.example.passerby.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import com.example.passerby.R;

import java.io.*;

import static android.accounts.AccountManager.KEY_INTENT;
import static android.app.Activity.RESULT_OK;

public abstract class ImagePickerFragment extends Fragment {
    public static final int REQUEST_CODE = 100;
    private static final int DRIVE_CODE = 105;
    private static final int GALLERY_REQUEST_CODE = 101;
    private static final int GALLERY_REQUEST_CODE_PDF = 103;
    private static final int CAMERA_REQUEST_CODE = 102;
    private String camera = "Camera", gallery = "Gallery", optionLabel = "Choose an option...";
    private String alert = "Alert!!!", cancel = "Cancel", openSettings = "Open Settings", ok = "Ok";
    private String permissionRequired = "Required camera and storage permission to access this functionality.";
    String[] item0 = {camera, gallery};
    String[] item1 = {gallery, cancel};
    Bitmap bmpPic;
    String imageAbsolutePath = " ", uid;
    private Activity mActivity;
    private int mCode = 0;

    public ImagePickerFragment() {
    }


    // code: 1 to show remove profile pic icon else 0.
    public void getImage(final Activity activity, int code) {
        mActivity = activity;
        mCode = code;
        if (!cameraPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    checkPermissionDenied(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
                } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    checkPermissionDenied(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
                } else {
                    requestPermission();
                }
            }

        } else {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
            mBuilder.setTitle(optionLabel).setItems(item0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (item0[which].equals(camera)) {
                        CaptureImage();
                    }
                    if (item0[which].equals(gallery)) {
                        OpenGallery();
                    }

                }
            }).create().show();
        }
    }


    public void openCamera(final Activity activity) {
        mActivity = activity;
        if (!cameraPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    checkPermissionDenied(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
                } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    checkPermissionDenied(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
                } else {
                    requestPermission();
                }
            }

        } else {
            CaptureImage();
        }
    }


    public void getPdfPicker(final Activity activity) {
        mActivity = activity;
        if (!cameraPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    checkPermissionDenied(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
                } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    checkPermissionDenied(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
                } else {
                    requestPermission();
                }
            }

        } else {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
            mBuilder.setTitle(optionLabel).setItems(item1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (item1[which].equals(cancel)) {

                    }
                    if (item1[which].equals(gallery)) {
                        OpenGalleryForPdf();
                    }

                }
            }).create().show();
        }
    }

    private void OpenGalleryForPdf() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a File"), GALLERY_REQUEST_CODE_PDF);
    }


    private boolean cameraPermission(String[] permissions) {
        return ContextCompat.checkSelfPermission(mActivity, permissions[0]) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(mActivity, permissions[1]) == PackageManager.PERMISSION_GRANTED;
    }

    void requestPermission() {
        ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_CODE);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            try {
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                int display_Width = displayMetrics.widthPixels;
//                Log.e("displayWIdth", "" + display_Width);

                bmpPic = decodeSampledBitmapFromFile(imageAbsolutePath,
                        display_Width / 2, display_Width / 2);
                String path = saveImageToExternalStorage(bmpPic);
                selectedImage(path);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedImage = data.getData();
                imageAbsolutePath = getPathFromUri(mActivity, selectedImage);
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                int display_Width = displayMetrics.widthPixels;
//                Log.e("displayWIdth", "" + display_Width);
                bmpPic = decodeSampledBitmapFromFile(imageAbsolutePath,
                        display_Width / 2, display_Width / 2);
                String path = saveImageToExternalStorage(bmpPic);
                selectedImage(path);
            }
        } else if (requestCode == DRIVE_CODE && resultCode == RESULT_OK) {
            selectedImage(data.getStringExtra(KEY_INTENT));
        } else if (requestCode == GALLERY_REQUEST_CODE_PDF && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedImage = data.getData();
                selectedImage(getPathFromUri(mActivity, selectedImage));
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String getPathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    private Bitmap decodeSampledBitmapFromFile(String pathName, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(pathName, options);// decodeResource(res,
        // resId, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        Log.i("Sample_Size", "" + options.inSampleSize);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return getbitmap(pathName, options);
    }


    public static Bitmap getbitmap(String image_Path, BitmapFactory.Options options) {
        Bitmap bm;
        Bitmap bitmap = null;
        try {
            bm = BitmapFactory.decodeStream(new FileInputStream(new File(
                    image_Path)), null, options);
            bitmap = bm;

            ExifInterface exif = new ExifInterface(image_Path);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 1);

            Matrix m = new Matrix();

            if ((orientation == ExifInterface.ORIENTATION_ROTATE_180)) {
                exif.setAttribute(ExifInterface.TAG_ORIENTATION, "3");
                exif.saveAttributes();

                m.postRotate(180);

                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);

                return bitmap;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                exif.setAttribute(ExifInterface.TAG_ORIENTATION, "6");
                exif.saveAttributes();

                m.postRotate(90);

                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);

                return bitmap;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                exif.setAttribute(ExifInterface.TAG_ORIENTATION, "8");
                exif.saveAttributes();
                m.postRotate(270);

                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
                return bitmap;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            Log.i("IOException:", e.toString());
            e.printStackTrace();
        }
        return bitmap;
    }


    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        Log.i("height", "" + height);
        Log.i("width", "" + width);
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public String saveImageToExternalStorage(Bitmap bitmap) {
        String ImageURi = null;
        try {
            File imageFile = new File(imageAbsolutePath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 60;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            ImageURi = imageFile.getAbsolutePath();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ImageURi;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getImage(mActivity, mCode);
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                checkPermissionDenied(permissions);
            }
        }
    }

    private void checkPermissionDenied(String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(permissions[0])) {
                android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(mActivity);
                mBuilder.setTitle(alert).setMessage(permissionRequired).setPositiveButton(ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermission();
                    }
                }).setNegativeButton(cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle(alert).setMessage(permissionRequired).setCancelable(false);
                builder.setPositiveButton(openSettings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mActivity.finish();
                        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", mActivity.getPackageName(), null));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).create().show();
            }
        }
    }

    public void OpenGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select a File"), GALLERY_REQUEST_CODE);
//        Intent pictureActionIntent;
//        pictureActionIntent = new Intent(Intent.ACTION_PICK,
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(pictureActionIntent, GALLERY_REQUEST_CODE);
    }

    public void CaptureImage() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File photoFile = createFileForImage();
            imageAbsolutePath = photoFile.getAbsolutePath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(mActivity.getApplicationContext(), mActivity.getApplicationContext().getPackageName() + ".provider", photoFile));
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public File createFileForImage() throws IOException {
        String imageFileName = "image" + System.currentTimeMillis() + ".jpg";
        File der = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), mActivity.getString(R.string.app_name));
        File image = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            if (!der.exists()) {
                der.mkdirs();
            }
            image = new File(der, imageFileName);
            imageAbsolutePath = image.getAbsolutePath();
        }
        return image;
    }

    public abstract void selectedImage(String imagePath);


}
