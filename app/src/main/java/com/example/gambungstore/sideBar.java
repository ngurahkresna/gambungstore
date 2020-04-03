package com.example.gambungstore;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gambungstore.progressbar.ProgressBarGambung;
import com.example.gambungstore.sharedpreference.SharedPreference;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link sideBar.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link sideBar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class sideBar extends Fragment {

    ProgressBarGambung mProgresBar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private LinearLayout mBackButton, mWishlistMenu, mEditProfile, mLogoutButton;
    TextView mUsernameSidebar;

    public sideBar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment sideBar.
     */
    // TODO: Rename and change types and number of parameters
    public static sideBar newInstance(String param1, String param2) {
        sideBar fragment = new sideBar();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mProgresBar= new ProgressBarGambung(getActivity());
        return inflater.inflate(R.layout.fragment_side_bar, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUsernameSidebar = getView().findViewById(R.id.usernameSidebar);
        mUsernameSidebar.setText(SharedPreference.getRegisteredUsername(getContext()));

        mBackButton = getView().findViewById(R.id.backButtonSidebar);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(sideBar.this).commit();
                ((homeActivity)getActivity()).refreshMenu();
                mProgresBar.startProgressBarGambung();
                Intent intent = new Intent(getContext(),homeActivity.class);
                startActivity(intent);

                getActivity().finish();
            }
        });

        mWishlistMenu = getView().findViewById(R.id.wishlistMenu);
        mWishlistMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), wishlistActivity.class);
                startActivity(intent);
                getFragmentManager().beginTransaction().remove(sideBar.this).commit();
                ((homeActivity)getActivity()).refreshMenu();
            }
        });

        mEditProfile = getView().findViewById(R.id.editProfileMenu);
        mEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), editProfile.class);
                startActivity(intent);
                getFragmentManager().beginTransaction().remove(sideBar.this).commit();
                ((homeActivity)getActivity()).refreshMenu();
            }
        });

        mLogoutButton = getView().findViewById(R.id.logoutButton);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutProcess();
                getFragmentManager().beginTransaction().remove(sideBar.this).commit();
                ((homeActivity)getActivity()).refreshMenu();
            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void logoutProcess(){
        SharedPreference.clearRegisteredId(getContext());
        SharedPreference.clearRegisteredToken(getContext());
        SharedPreference.clearRegisteredUsername(getContext());
    }
}
