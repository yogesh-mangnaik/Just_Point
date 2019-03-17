package com.anonymous.thedailyprophetproject;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;
    ArFragment arFragment;
    boolean shouldAddModel = true;
    View view;
    ProgressDialog progressDialog;

    @Override
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        arFragment = (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);
        arFragment.getPlaneDiscoveryController().hide();

        view = getLayoutInflater().inflate(R.layout.business_card_layout, null);

        arFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdateFrame);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void onUpdateFrame(FrameTime frameTime) {
        Frame frame = arFragment.getArSceneView().getArFrame();
        Collection<AugmentedImage> augmentedImages = frame.getUpdatedTrackables(AugmentedImage.class);
        for (AugmentedImage augmentedImage : augmentedImages) {
            if (augmentedImage.getTrackingState() == TrackingState.TRACKING) {
                String name = augmentedImage.getName();
                if(Constants.cardIndex.containsKey(name) && shouldAddModel){
                    int index = Constants.cardIndex.get(name);
                    System.out.println("Found image");
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(500);
                    //placeObject(arFragment, augmentedImage.createAnchor(augmentedImage.getCenterPose()), Uri.parse("Lamborghini_Aventador.sfb"));
                    ViewRenderable.builder()
                            .setView(this, view)
                            .build()
                            .thenAccept(modelRenderable -> addNodeToScene(arFragment, augmentedImage.createAnchor(augmentedImage.getCenterPose()), modelRenderable));
                    Button button = (Button) view.findViewById(R.id.button_navigate);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                            startActivity(i);
                        }
                    });
                    shouldAddModel = false;
                }
//                if (augmentedImage.getName().equals("card") && shouldAddModel) {
//
//                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeObject(ArFragment arFragment, Anchor anchor, Uri uri) {
        System.out.println("Placing Object");
        ModelRenderable.builder()
                .setSource(arFragment.getContext(), uri)
                .build()
                .thenAccept(modelRenderable -> addNodeToScene(arFragment, anchor, modelRenderable))
                .exceptionally(throwable -> {
                            Toast.makeText(arFragment.getContext(), "Error:" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                            return null;
                        }
                );
    }

    private void addNodeToScene(ArFragment arFragment, Anchor anchor, Renderable renderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        //anchorNode.setLocalRotation(Quaternion.axisAngle(new Vector3(0f,0f,-1f), 90f));
        TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());

        node.setWorldRotation(new Quaternion(Quaternion.axisAngle(Vector3.up(), 0f)));
        node.getScaleController().setMaxScale(0.07f);
        node.getScaleController().setMinScale(0.02f);
        node.setWorldRotation(Quaternion.axisAngle(new Vector3(1f, 0f, 0f), 270));
        node.setLocalPosition(new Vector3(0,0f,.0001f));

        node.setRenderable(renderable);
        node.setParent(anchorNode);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        node.select();
    }

    public boolean setupAugmentedImagesDb(Config config, Session session) {
        System.out.println("Setting up Augmented image database");
        AugmentedImageDatabase augmentedImageDatabase;
        augmentedImageDatabase = new AugmentedImageDatabase(session);
        for(int i=0; i<Constants.cards.size(); i++){
            Bitmap bitmap = loadAugmentedImage(i);
            if (bitmap == null) {
                return false;
            }
            augmentedImageDatabase.addImage(Constants.cards.get(i).augmentingImage, bitmap);
        }
        config.setAugmentedImageDatabase(augmentedImageDatabase);
        System.out.println("Augmented image database set up");
        progressDialog.hide();
        return true;
    }

    private Bitmap loadAugmentedImage(int index) {
        System.out.println("Loading Augmented Images");
        try (InputStream is = getAssets().open(Constants.cards.get(index).augmentingImage+".png")) {
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            Log.e("ImageLoad", "IO Exception", e);
        }
        return null;
    }

    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }
}