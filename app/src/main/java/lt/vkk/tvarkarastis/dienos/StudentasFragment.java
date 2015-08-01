package lt.vkk.tvarkarastis.dienos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import lt.vkk.tvarkarastis.R;
import lt.vkk.tvarkarastis.adapters.GrupePaskaitaAdapter;
import lt.vkk.tvarkarastis.models.PaskaitosIrasas;

public class StudentasFragment extends Fragment {

    private static final String ARG_LIST = "list";

    @Bind(R.id.listViewStudentas)
    ListView mListView;

    private ArrayList<PaskaitosIrasas> list;

    public static StudentasFragment newInstance(ArrayList<PaskaitosIrasas> list) {
        StudentasFragment fragment = new StudentasFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_LIST, list);
        fragment.setArguments(args);
        return fragment;
    }

    public StudentasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = getArguments().getParcelableArrayList(ARG_LIST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_studentas, container, false);
        ButterKnife.bind(this, rootView);
        GrupePaskaitaAdapter adapter = new GrupePaskaitaAdapter(getActivity(), R.layout.listview_item_row_paskaita_studentas, list);
        mListView.setAdapter(adapter);
        return rootView;
    }

}
