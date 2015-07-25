package lt.vkk.tvarkarastis.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import lt.vkk.tvarkarastis.R;
import lt.vkk.tvarkarastis.models.PaskaitosIrasas;

/**
 * Created by alius on 2015.07.24.
 */
public class GrupePaskaitaAdapter extends ArrayAdapter<PaskaitosIrasas>{
    Context context;
    int layoutResourceId;
    ArrayList<PaskaitosIrasas> data = null;

    public GrupePaskaitaAdapter(Context context, int resource, ArrayList<PaskaitosIrasas> objects) {
        super(context, resource, objects);
        this.layoutResourceId = resource;
        this.context = context;
        this.data = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        GrupePaskaitaHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new GrupePaskaitaHolder();
            holder.txtPaskaitosPradzia = (TextView) row.findViewById(R.id.txtPaskaitosPradzia);
            holder.txtAuditorija = (TextView) row.findViewById(R.id.txtAuditorija);
            holder.txtPaskaitosPavadinimas = (TextView) row.findViewById(R.id.txtPaskaitosPavadinimas);
            holder.txtDestytojas = (TextView) row.findViewById(R.id.txtDestytojas);

            row.setTag(holder);
        } else {
            holder = (GrupePaskaitaHolder) row.getTag();
        }
/*
        //System.out.println(data.toArray());
        for (PaskaitosIrasas element : data) {
            Log.v("aliusa remote iD:", String.valueOf(element.getId()));
            System.out.println(element.getId());
            System.out.println(element.savDiena);
            System.out.println(element.pradzia);
            System.out.println(element.pabaiga);
            System.out.println(element.grupe.pavadinimas);
            System.out.println(element.dalykas);
            System.out.println(element.destytojas.pavarde);
            System.out.println(element.auditorija);
            System.out.println(element.pogrupis);
            System.out.println(element.pasikatojamumas);
            System.out.println(element.pasirenkamasis);
        }*/
        PaskaitosIrasas paskaitosIrasas = data.get(position);
        holder.txtPaskaitosPradzia.setText(paskaitosIrasas.pradzia + " - " + paskaitosIrasas.pabaiga);
        holder.txtAuditorija.setText(paskaitosIrasas.auditorija);
        holder.txtPaskaitosPavadinimas.setText(paskaitosIrasas.dalykas);
        holder.txtDestytojas.setText(paskaitosIrasas.destytojas.pavarde + ", " + paskaitosIrasas.destytojas.vardas);
        return row;
    }

    static class GrupePaskaitaHolder {
        TextView txtPaskaitosPradzia;
        TextView txtAuditorija;
        TextView txtPaskaitosPavadinimas;
        TextView txtDestytojas;
    }
}