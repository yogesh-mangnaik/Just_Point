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
import android.widget.ImageView;
import android.widget.TextView;
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
    boolean shouldAddPiyush = true;
    boolean shouldAddCard = true;
    boolean shouldAddBurgerKing = true;
    boolean shouldAddKfc = true;
    boolean shouldAddMcd = true;
    boolean shouldAddPizzaHut = true;
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
        //progressDialog.show();

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
                if (augmentedImage.getName().equals("yogesh") && shouldAddCard) {
                    System.out.println("Found image");
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(500);
                    //placeObject(arFragment, augmentedImage.createAnchor(augmentedImage.getCenterPose()), Uri.parse("earth_obj.sfb"));
                    View view = getLayoutInflater().inflate(R.layout.business_card_layout, null);
                    ViewRenderable.builder()
                            .setView(this, view)
                            .build()
                            .thenAccept(modelRenderable -> addNodeToScene(arFragment, augmentedImage.createAnchor(augmentedImage.getCenterPose()), modelRenderable));
                    ((TextView)view.findViewById(R.id.name)).setText("Yogesh Mangnaik");
                    ((TextView)view.findViewById(R.id.description)).setText("Team Developer\nFinal Year BTech Information Technology,\nVJTI, Mumbai.");
                    ((ImageView)view.findViewById(R.id.profile_imageview)).setImageResource(R.drawable.yogesh);
                    ((Button)view.findViewById(R.id.button_navigate)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this, "This will open navigation to the persons office", Toast.LENGTH_SHORT).show();
                        }
                    });
                    shouldAddCard = false;
                }
                else if(augmentedImage.getName().equals("piyush") && shouldAddPiyush) {
                    System.out.println("Found image");
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(500);
                    //placeObject(arFragment, augmentedImage.createAnchor(augmentedImage.getCenterPose()), Uri.parse("earth_obj.sfb"));
                    View view = getLayoutInflater().inflate(R.layout.business_card_layout, null);
                    ViewRenderable.builder()
                            .setView(this, view)
                            .build()
                            .thenAccept(modelRenderable -> addNodeToScene(arFragment, augmentedImage.createAnchor(augmentedImage.getCenterPose()), modelRenderable));
                    ((TextView)view.findViewById(R.id.name)).setText("Piyush Pawar");
                    ((TextView)view.findViewById(R.id.description)).setText("Final Year B.Tech. Information Technology\nSmart Boy\nStudies in VJTI");
                    ((ImageView)view.findViewById(R.id.profile_imageview)).setImageResource(R.drawable.devs2);
                    ((Button)view.findViewById(R.id.button_navigate)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this, "This will open navigation to the persons office", Toast.LENGTH_SHORT).show();
                        }
                    });
                    shouldAddPiyush = false;
                }
                else if(augmentedImage.getName().equals("burgerking") && shouldAddBurgerKing) {
                    System.out.println("Found shop image");
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(500);
                    //placeObject(arFragment, augmentedImage.createAnchor(augmentedImage.getCenterPose()), Uri.parse("earth_obj.sfb"));
                    View view = getLayoutInflater().inflate(R.layout.business_block_layout, null);
                    ViewRenderable.builder()
                            .setView(this, view)
                            .build()
                            .thenAccept(modelRenderable -> addNodeToScene2(arFragment, augmentedImage.createAnchor(augmentedImage.getCenterPose()), modelRenderable));
                    ((TextView)view.findViewById(R.id.tv_shop_name)).setText("Burger King");
                    ((TextView)view.findViewById(R.id.tv_shop_price)).setText("Rs. 500 for two");
                    ((TextView)view.findViewById(R.id.tv_shop_offers)).setText("1. Two veg burgers @ Rs. 70\n\n2. Two chicken burgers @ Rs. 100");
                    ((ImageView)view.findViewById(R.id.iv_shop_logo)).setImageResource(R.drawable.burgerking);
                    ((TextView)view.findViewById(R.id.tv_shop_rating)).setText("4/5 Stars");
                    shouldAddBurgerKing = false;
                }
                else if(augmentedImage.getName().equals("kfc") && shouldAddKfc) {
                    System.out.println("Found shop image");
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(500);
                    //placeObject(arFragment, augmentedImage.createAnchor(augmentedImage.getCenterPose()), Uri.parse("earth_obj.sfb"));
                    View view = getLayoutInflater().inflate(R.layout.business_block_layout, null);
                    ViewRenderable.builder()
                            .setView(this, view)
                            .build()
                            .thenAccept(modelRenderable -> addNodeToScene2(arFragment, augmentedImage.createAnchor(augmentedImage.getCenterPose()), modelRenderable));
                    ((TextView)view.findViewById(R.id.tv_shop_name)).setText("KFC");
                    ((TextView)view.findViewById(R.id.tv_shop_price)).setText("Rs. 800 for two");
                    ((TextView)view.findViewById(R.id.tv_shop_offers)).setText("1. Chicken Bucket at Rs.599\n\n2. Chicken Popcorn @ Rs. 79");
                    ((ImageView)view.findViewById(R.id.iv_shop_logo)).setImageResource(R.drawable.kfc);
                    ((TextView)view.findViewById(R.id.tv_shop_rating)).setText("3/5 Stars");
                    shouldAddKfc = false;
                }
                else if(augmentedImage.getName().equals("mcd") && shouldAddMcd) {
                    System.out.println("Found shop image");
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(500);
                    //placeObject(arFragment, augmentedImage.createAnchor(augmentedImage.getCenterPose()), Uri.parse("earth_obj.sfb"));
                    View view = getLayoutInflater().inflate(R.layout.business_block_layout, null);
                    ViewRenderable.builder()
                            .setView(this, view)
                            .build()
                            .thenAccept(modelRenderable -> addNodeToScene2(arFragment, augmentedImage.createAnchor(augmentedImage.getCenterPose()), modelRenderable));
                    ((TextView)view.findViewById(R.id.tv_shop_name)).setText("McDonald's");
                    ((TextView)view.findViewById(R.id.tv_shop_price)).setText("Rs. 600 for two");
                    ((TextView)view.findViewById(R.id.tv_shop_offers)).setText("1. Two veg burgers @ Rs. 70\n\n2. Two chicken burgers @ Rs. 100");
                    ((ImageView)view.findViewById(R.id.iv_shop_logo)).setImageResource(R.drawable.mcd);
                    ((TextView)view.findViewById(R.id.tv_shop_rating)).setText("5/5 Stars");
                    shouldAddMcd = false;
                }
                else if(augmentedImage.getName().equals("pizzahut") && shouldAddPizzaHut) {
                    System.out.println("Found shop image");
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(500);
                    //placeObject(arFragment, augmentedImage.createAnchor(augmentedImage.getCenterPose()), Uri.parse("earth_obj.sfb"));
                    View view = getLayoutInflater().inflate(R.layout.business_block_layout, null);
                    ViewRenderable.builder()
                            .setView(this, view)
                            .build()
                            .thenAccept(modelRenderable -> addNodeToScene2(arFragment, augmentedImage.createAnchor(augmentedImage.getCenterPose()), modelRenderable));
                    ((TextView)view.findViewById(R.id.tv_shop_name)).setText("Pizza Hut");
                    ((TextView)view.findViewById(R.id.tv_shop_price)).setText("Rs. 700 for two");
                    ((TextView)view.findViewById(R.id.tv_shop_offers)).setText("1. Two Medium Pizza @ Rs. 199 each\n\n2. Pan Pizza @ Rs. 99 each");
                    ((ImageView)view.findViewById(R.id.iv_shop_logo)).setImageResource(R.drawable.pizzahut);
                    ((TextView)view.findViewById(R.id.tv_shop_rating)).setText("3.5/5 Stars");
                    shouldAddPizzaHut = false;
                }

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

    private void addNodeToScene2(ArFragment arFragment, Anchor anchor, Renderable renderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        //anchorNode.setLocalRotation(Quaternion.axisAngle(new Vector3(0f,0f,-1f), 90f));
        TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());

        node.setWorldRotation(new Quaternion(Quaternion.axisAngle(Vector3.up(), 0f)));
        node.getScaleController().setMaxScale(0.07f);
        node.getScaleController().setMinScale(0.05f);
        node.setWorldRotation(Quaternion.axisAngle(new Vector3(1f, 0f, 0f), 270));
        node.setLocalPosition(new Vector3(0f,0.01f,.0001f));

        node.setRenderable(renderable);
        node.setParent(anchorNode);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        node.select();
    }

    public boolean setupAugmentedImagesDb(Config config, Session session) {
        System.out.println("Setting up Augmented image database");
        AugmentedImageDatabase augmentedImageDatabase;
        augmentedImageDatabase = new AugmentedImageDatabase(session);
        Bitmap bitmap = loadAugmentedImage("yogesh.png");
        if (bitmap == null) {
            return false;
        }
        augmentedImageDatabase.addImage("yogesh", bitmap);
        bitmap = loadAugmentedImage("piyush.png");
        if(bitmap == null)
            return false;
        augmentedImageDatabase.addImage("piyush", bitmap);
        bitmap = loadAugmentedImage("burgerking.png");
        if(bitmap == null)
            return false;
        augmentedImageDatabase.addImage("burgerking", bitmap);
        bitmap = loadAugmentedImage("kfc.png");
        if(bitmap == null)
            return false;
        augmentedImageDatabase.addImage("kfc", bitmap);
        bitmap = loadAugmentedImage("mcd.png");
        if(bitmap == null)
            return false;
        augmentedImageDatabase.addImage("mcd", bitmap);
        bitmap = loadAugmentedImage("pizzahut.png");
        if(bitmap == null)
            return false;
        augmentedImageDatabase.addImage("pizzahut", bitmap);
        config.setAugmentedImageDatabase(augmentedImageDatabase);
        System.out.println("Augmented image database set up");
        return true;
    }

    private Bitmap loadAugmentedImage(String data) {
        System.out.println("Loading Augmented Images");
        try (InputStream is = getAssets().open(data)) {
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