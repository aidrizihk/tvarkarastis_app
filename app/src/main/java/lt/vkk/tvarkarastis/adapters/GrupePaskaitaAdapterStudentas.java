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
public class GrupePaskaitaAdapterStudentas extends ArrayAdapter<PaskaitosIrasas> {
    Context context;
    int layoutResourceId;
    ArrayList<PaskaitosIrasas> data = null;

    public GrupePaskaitaAdapterStudentas(Context context, int resource, ArrayList<PaskaitosIrasas> objects) {
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

        PaskaitosIrasas paskaitosIrasas = data.get(position);
        holder.txtPaskaitosPradzia.setText(paskaitosIrasas.getPradzia() + " - " + paskaitosIrasas.getPabaiga());
        holder.txtAuditorija.setText(paskaitosIrasas.getAuditorija());
        holder.txtPaskaitosPavadinimas.setText(paskaitosIrasas.getDalykas());
        holder.txtDestytojas.setText(paskaitosIrasas.getDestytojas().getPavarde() + ", " + paskaitosIrasas.getDestytojas().getVardas());
        return row;
    }

    static class GrupePaskaitaHolder {
        TextView txtPaskaitosPradzia;
        TextView txtAuditorija;
        TextView txtPaskaitosPavadinimas;
        TextView txtDestytojas;
    }
}