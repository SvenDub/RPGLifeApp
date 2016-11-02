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
import android.widget.ListView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;

import questionablequality.rpglifeapp.AddQuestActivity;
import questionablequality.rpglifeapp.ApiController;
import questionablequality.rpglifeapp.GuildActivity;
import questionablequality.rpglifeapp.LoginActivity;
import questionablequality.rpglifeapp.MainMenuActivity;
import questionablequality.rpglifeapp.QuestLogActivity;
import questionablequality.rpglifeapp.R;
import questionablequality.rpglifeapp.adapter.GuildAdapter;
import questionablequality.rpglifeapp.adapter.QuestAdapter;
import questionablequality.rpglifeapp.data.User;
import questionablequality.rpglifeapp.provider.GuildMemberProvider;
import questionablequality.rpglifeapp.provider.GuildQuestProvider;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GuildFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GuildFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuildFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    private User mUser;
    private ApiController mApiController;

    private GuildQuestProvider mGuildQuestProvider;
    private QuestAdapter mQuestAdapter;

    private GuildMemberProvider mGuildMemberProvider;
    private GuildAdapter mGuildAdapter;

    Button addquest;

    public GuildFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GuildFragment.
     */
    public static GuildFragment newInstance() {
        GuildFragment fragment = new GuildFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guild, container, false);

        addquest = (Button)view.findViewById(R.id.BtnAddGuildQuest);

        mApiController = new ApiController(view.getContext());

        /**
        //binds the adapter containing the quests.
        mGuildQuestProvider = new GuildQuestProvider(view.getContext());
        mQuestAdapter = new QuestAdapter(view.getContext(), mGuildQuestProvider.ReturnQuests());
        ListView quests = (ListView)view.findViewById(R.id.LstQuests);
        quests.setAdapter(mQuestAdapter);

        //binds the adapter containing the guildmembers.
        mGuildMemberProvider = new GuildMemberProvider(view.getContext());
        mGuildAdapter = new GuildAdapter(view.getContext(), mGuildMemberProvider.ReturnMembers());
        ListView members = (ListView)view.findViewById(R.id.LstMembers);
        members.setAdapter(mGuildAdapter);
         **/



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
            mUser = result.getResult();

            if(mUser.getGuild().hasGuildLeader()){
                if(!(mUser == mUser.getGuild().getGuildLeader())){
                    addquest.setEnabled(false);
                }else{
                    addquest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), AddQuestActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }else{
                addquest.setText("Claim Guildleadership");
                addquest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mUser.getGuild().setGuildLeader(mUser);
                        //TODO: save guild
                    }
                });
            }

        }
    };
}
