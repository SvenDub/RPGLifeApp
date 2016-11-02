package questionablequality.rpglifeapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;

import questionablequality.rpglifeapp.ApiController;
import questionablequality.rpglifeapp.LoginActivity;
import questionablequality.rpglifeapp.MainMenuActivity;
import questionablequality.rpglifeapp.R;
import questionablequality.rpglifeapp.data.User;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JoinGuildFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JoinGuildFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JoinGuildFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private User mUser;
    private ApiController mApiController;

    public JoinGuildFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JoinGuildFragment.
     */
    public static JoinGuildFragment newInstance() {
        JoinGuildFragment fragment = new JoinGuildFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join_guild, container, false);
        mApiController = new ApiController(view.getContext());
        EditText code = (EditText) view.findViewById(R.id.TxtSetGuildCode);
        Button button = (Button) view.findViewById(R.id.BtnFindGuild);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApiController.getUser(mUserCallback);
                getActivity().finish();
            }
        });


        return view;
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

    }

    private FutureCallback<Response<User >> mUserCallback = new FutureCallback<Response<User>>() {
        @Override
        public void onCompleted(Exception e, Response<User> result) {
            if (e == null && result.getException() == null && result.getResult() != null) {
                //TODO: fix joining guild.
                mUser = result.getResult();

            }
        }
    };
}
