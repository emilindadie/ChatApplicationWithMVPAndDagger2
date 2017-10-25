package com.example.emili.firstapp.ui.profilActivity.profilActivityFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.emili.firstapp.R;
import com.example.emili.firstapp.app.ChatApplication;
import com.example.emili.firstapp.dagger.DaggerProfilActivityComponent;
import com.example.emili.firstapp.dagger.ProfilActivityComponent;
import com.example.emili.firstapp.dagger.ProfilActivityModule;
import com.example.emili.firstapp.data.FirebaseHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProfilActivityFragment extends Fragment implements UserProfilView{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfilActivityFragment() {
        // Required empty public constructor
    }

    @Inject
    FirebaseHelper firebaseHelper;
    @Inject UserProfilPresenter userProfilPresenter;

    String urlPictureImage;

    @BindView(R.id.firstName)
    TextView textViewFirstName;

    @BindView(R.id.lastName)
    TextView textViewLastName;

    @BindView(R.id.email)
    TextView textViewEmail;

    @BindView(R.id.profilImageView)
    ImageView profilImage;

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
    public static ProfilActivityFragment newInstance(String param1, String param2) {
        ProfilActivityFragment fragment = new ProfilActivityFragment();
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
        return inflater.inflate(R.layout.fragment_profil_activity, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(getActivity(), view);
        userProfilPresenter.loadUserData();
        makeDefaultPicture(profilImage, urlPictureImage);
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
    public void showFirstName(String firstName) {
    textViewFirstName.setText(firstName);
    }

    @Override
    public void showLastName(String lastName) {
        textViewLastName.setText(lastName);
    }

    @Override
    public void showEmail(String email) {
        textViewEmail.setText(email);
    }

    @Override
    public void showUrlProfilPicture(String url) {
        urlPictureImage = url;
    }

    void makeDefaultPicture(ImageView imageView, String url){
        Glide.with(this)
                .load(url)
                .override(100, 100)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
