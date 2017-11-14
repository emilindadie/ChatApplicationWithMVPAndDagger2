package com.example.emili.firstapp.ui.profilActivity.firstConnectingFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.emili.firstapp.R;
import com.example.emili.firstapp.app.ChatApplication;
import com.example.emili.firstapp.dagger.DaggerProfilActivityComponent;
import com.example.emili.firstapp.dagger.ProfilActivityComponent;
import com.example.emili.firstapp.dagger.ProfilActivityModule;
import com.example.emili.firstapp.data.FirebaseHelper;
import com.example.emili.firstapp.ui.profilActivity.profilActivityFragment.ProfilActivityFragment;

import javax.inject.Inject;


import static android.app.Activity.RESULT_OK;

public class FirstConnectingFragment extends Fragment implements FirstConnectingSettingView, View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    String urlDefaulPicture;

    //@BindView(R.id.identifiant)
    TextView identifiant;

    //@BindView(R.id.fileIndicator)
    TextView fileIndicator;

    //@BindView(R.id.defaultImageView)
    ImageView defaultimageProfil;
    Uri updateProfilImage;


    //@BindView(R.id.buttonBegin)
    Button begin;
    String firstName="";
    String lastName="";
    boolean cliquer = false;
    private static final int RC_PHOTO_PICKER = 2;

    @Inject FirebaseHelper firebaseHelper;
    @Inject FirstConnectingSettingPresenter firstConnectingSettingPresenter;

    public FirstConnectingFragment() {
        // Required empty public constructor
    }

    private ProfilActivityComponent profilActivityComponent;

    public ProfilActivityComponent getProfilActivityComponent(){
        if(profilActivityComponent == null){
            profilActivityComponent = DaggerProfilActivityComponent.builder()
                    .profilActivityModule(new ProfilActivityModule(getActivity()))
                    .applicationComponent(ChatApplication.get(getActivity()).getApplicationComponent())
                    .build();
        }
        return profilActivityComponent;
    }

    // TODO: Rename and change types and number of parameters
    public static FirstConnectingFragment newInstance(String param1, String param2) {
        FirstConnectingFragment fragment = new FirstConnectingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        getProfilActivityComponent().inject(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_connecting, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        identifiant = (TextView) view.findViewById(R.id.identifiant);
        fileIndicator = (TextView) view.findViewById(R.id.fileIndicator);
        defaultimageProfil = (ImageView) view.findViewById(R.id.defaultImageView);
        begin = (Button) view.findViewById(R.id.buttonBegin);
        begin.setOnClickListener(this);

        //ButterKnife.bind(getActivity(), view);
        firstConnectingSettingPresenter.setFirstConnectingSettingView(this);
        firstConnectingSettingPresenter.loadUserDefaultProfilPicture();
        makeDefaultPicture(defaultimageProfil, urlDefaulPicture);
        identifiant.setText("Bonjour "+firstName +" "+lastName);
        fileIndicator.setVisibility(View.INVISIBLE);
        defaultimageProfil.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showUserFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public void showUserLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public void showUrlProfilPicture(String url) {
      this.urlDefaulPicture = url;
    }

    @Override
    public void showSuccessUpdateProfilPicture() {
        Toast.makeText(getActivity(), "La photo a bien été envoiyé", Toast.LENGTH_LONG).show();
        try {
            wait(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        goToProfilActivity();
    }

    @Override
    public void showErrorUpdateProfilPicture() {
        Toast.makeText(getActivity(), "La photo n'a pas été envoiyé", Toast.LENGTH_LONG).show();
    }

    private void goToProfilActivity() {
        firstConnectingSettingPresenter.updateBooleanFirstConnecting();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutProfilActivity, new ProfilActivityFragment()).commit();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.defaultImageView:
                startInterneGalleryPicture();
                break;

            case R.id.buttonBegin:

                if(updateProfilImage != null){
                    fileIndicator.setVisibility(View.VISIBLE);
                    fileIndicator.setText(updateProfilImage.toString().substring(0, 20));
                    firstConnectingSettingPresenter.updateProfilPicture(updateProfilImage);
                }
                else {
                    goToProfilActivity();
                }
                break;
        }
    }

    private void startInterneGalleryPicture() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    void makeDefaultPicture(ImageView imageView, String url){
        Glide.with(this)
                .load(url)
                .override(100, 100)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            // Sign in was canceled by the user, finish the activity
                this.updateProfilImage = data.getData();
        }
    }
}
