package questionablequality.rpglifeapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;

import java.util.List;

import questionablequality.rpglifeapp.AddQuestActivity;
import questionablequality.rpglifeapp.ApiController;
import questionablequality.rpglifeapp.QuestDetailActivity;
import questionablequality.rpglifeapp.R;
import questionablequality.rpglifeapp.adapter.GuildAdapter;
import questionablequality.rpglifeapp.adapter.GuildQuestAdapter;
import questionablequality.rpglifeapp.data.Quest;
import questionablequality.rpglifeapp.data.User;
import questionablequality.rpglifeapp.provider.GuildProvider;
import questionablequality.rpglifeapp.provider.QuestProvider;

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

    private QuestProvider mGuildQuestProvider;
    private GuildQuestAdapter mGuildQuestAdapter;

    private GuildProvider mGuildProvider;
    private GuildAdapter mGuildAdapter;

    private Button addquest;
    private ListView mLstQuests;

    private View view;

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
        view = inflater.inflate(R.layout.fragment_guild, container, false);

        addquest = (Button)view.findViewById(R.id.BtnAddGuildQuest);

        mLstQuests = (ListView) view.findViewById(R.id.LstQuests);

        mApiController = new ApiController(view.getContext());
        mApiController.getUser(mUserCallback);

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
    public void onResume() {
        super.onResume();

        if (mGuildQuestProvider != null) {
            List<Quest> quests = mGuildQuestProvider.ReturnQuests();
            mGuildQuestAdapter.clear();
            mGuildQuestAdapter.addAll(quests);

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

    private FutureCallback<Response<User>> mUserCallback = new FutureCallback<Response<User>>() {
        @Override
        public void onCompleted(Exception e, Response<User> result) {
            mUser = result.getResult();

            if(mUser.getGuild().hasGuildLeader()){
                if(mUser.getId() == mUser.getGuild().getGuildLeader(view.getContext()).getId()){
                    addquest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), AddQuestActivity.class);
                            intent.putExtra("isGuild", true);
                            startActivity(intent);
                        }
                    });
                }else{
                    addquest.setEnabled(false);
                }
            }

            //binds the adapter containing the quests.
            mGuildQuestProvider = new QuestProvider(view.getContext());
            mGuildQuestAdapter = new GuildQuestAdapter(view.getContext(), mGuildQuestProvider.ReturnGuildQuests());
            ListView quests = (ListView)view.findViewById(R.id.LstQuests);
            quests.setAdapter(mGuildQuestAdapter);

            //binds the adapter containing the guildmembers.
            mGuildProvider = new GuildProvider(view.getContext());
            mGuildAdapter = new GuildAdapter(view.getContext(), mGuildProvider.ReturnGuildMembers(mUser));
            ListView members = (ListView)view.findViewById(R.id.LstMembers);
            members.setAdapter(mGuildAdapter);

            TextView text = (TextView)view.findViewById(R.id.TxtGuilLeader);
            text.setText(mUser.getGuild().getGuildLeaderString(view.getContext()));

            text = (TextView)view.findViewById(R.id.TxtCode);
            text.setText(mUser.getGuild().getCodeString());


            Button BtnBack = (Button) view.findViewById(R.id.BtnGuildBack);
            BtnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });

            mLstQuests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Quest quest = mGuildQuestAdapter.getItem(position);

                    Intent intent = new Intent(GuildFragment.this.getActivity(), QuestDetailActivity.class);
                    intent.putExtra("quest", quest.getId());

                    startActivity(intent);
                }
            });
        }
    };
}
