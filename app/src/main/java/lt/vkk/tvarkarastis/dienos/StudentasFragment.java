package lt.vkk.tvarkarastis.dienos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import lt.vkk.tvarkarastis.R;
import lt.vkk.tvarkarastis.adapters.GrupePaskaitaAdapter;
import lt.vkk.tvarkarastis.models.PaskaitosIrasas;

public class StudentasFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    static ArrayList<PaskaitosIrasas> list;

    public static StudentasFragment newInstance(ArrayList<PaskaitosIrasas> paskaitos) {
        StudentasFragment fragment = new StudentasFragment();
        //Bundle args = new Bundle();
        list = paskaitos;
        //args.putAll(args);
        //fragment.setArguments(args);
        return fragment;
    }

    public StudentasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_studentas, container, false);

        ListView lv = (ListView)v.findViewById(R.id.listViewStudentas);

        GrupePaskaitaAdapter adapter = new GrupePaskaitaAdapter(getActivity(), R.layout.listview_item_row_paskaita_studentas, list);

        lv.setAdapter(adapter);

        return v;
    }

}
