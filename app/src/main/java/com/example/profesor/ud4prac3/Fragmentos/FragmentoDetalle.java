package com.example.profesor.ud4prac3.Fragmentos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.profesor.ud4prac3.Alumnos.Alumno;
import com.example.profesor.ud4prac3.R;

/**
 * A simple {@link Fragment} subclass.
 */

public class FragmentoDetalle extends Fragment implements View.OnClickListener{

    //Widgets
    private Button boton_modificar;
    private Button boton_guardar;
    private EditText editText_nombre;
    private EditText editText_apellido;
    private RelativeLayout layout_fragment;
    private Spinner spinnerCiclo;
    private RadioGroup radioGroup;
    private RadioButton radioButtonMujer;
    private RadioButton radioButtonHombre;

    //Fragmento suscrito a nuestros callbacks
    private FragmentoDetalleListener escuchador;

    //Alumno con el que trabajaremos
    private Alumno alumno;

    //Este constructor debe estar vacío (cosas raras de la arquitectura Android)
    public FragmentoDetalle() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflamos (cargamos) el layout
        View layout = inflater.inflate(R.layout.fragment_fragmento_detalle, container, false);
        //Instanciamos los widgets del layout del fragment para poder operar con ellos
        layout_fragment = (RelativeLayout)layout.findViewById(R.id.relativeLayaout_main_frag_detalle);
        editText_nombre = (EditText)layout.findViewById(R.id.editText_nombre);
        editText_apellido = (EditText)layout.findViewById(R.id.editText_apellido);
        boton_guardar = (Button)layout.findViewById(R.id.boton_guardar);
        boton_modificar = (Button)layout.findViewById(R.id.boton_modificar);
        spinnerCiclo = (Spinner)layout.findViewById(R.id.spinner_ciclo);
        radioGroup = (RadioGroup)layout.findViewById(R.id.radioGroup);
        radioButtonMujer =(RadioButton)layout.findViewById(R.id.radioButton_mujer);
        radioButtonHombre =(RadioButton)layout.findViewById(R.id.radioButton_hombre);

        //Nos erigimos en oyentes de los botones modificar y guardar
        boton_guardar.setOnClickListener(this);
        boton_modificar.setOnClickListener(this);

        //Invisibilizamos los widgets hasta que se nos envíe un alumno
        layout_fragment.setVisibility(View.INVISIBLE);
        return layout;
    }

    //Métodos que expondremos a la activity para que nos proporcione un alumno y
    //trabajemos con él
    public void setAlumno (Alumno alumno){
        //Guardamos el alumno
        this.alumno=alumno;
        //Desactivamos widgets
        desactivarWidgets();
        //Llenamos de contenido los widgets
        editText_nombre.setText(alumno.getNombre());
        editText_apellido.setText(alumno.getApellido());
        //Preparamos el adaptador
        ArrayAdapter<String> adaptadorSpinner= new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,Alumno.arrayCiclo);
        //Configuramos el spinner
        spinnerCiclo.setAdapter(adaptadorSpinner);
        spinnerCiclo.setSelection(adaptadorSpinner.getPosition(alumno.getCiclo()));
        spinnerCiclo.setEnabled(false);
        //Configuramos el RadioGroup
        if (alumno.isMujer()){
            radioGroup.check(R.id.radioButton_mujer);
        }
        else {
            radioGroup.check(R.id.radioButton_hombre);
        }
        layout_fragment.setVisibility(View.VISIBLE);
    }

    //Implementamos el callback de los botones
    @Override
    public void onClick(View v) {
        if(v.getId() == boton_modificar.getId()){
            //Activamos widgets
            activarWidgets();
        }
        else if (v.getId() == boton_guardar.getId()){
            //Creo el diálogo
            AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
            //Le seteo el título y el mensaje
            alerta.setTitle(R.string.string_dialogo_cambioalumno_titulo);
            alerta.setMessage(getResources().getString(R.string.string_dialogo_cambioalumno_mensaje) + "\n" +alumno.toString());
            //Le seteo el botón aceptar y implemento su callback
            alerta.setPositiveButton(R.string.string_dialogo_cambioalumno_botonAceptar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Modificamos el alumno
                    alumno.setNombre(editText_nombre.getText().toString());
                    alumno.setApellido(editText_apellido.getText().toString());
                    alumno.setCiclo(spinnerCiclo.getSelectedItem().toString());
                    if (radioGroup.getCheckedRadioButtonId() == R.id.radioButton_mujer){
                        alumno.setMujer(true);
                    }
                    else {
                        alumno.setMujer(false);
                    }
                    //Invisibilizamos el fragment
                    layout_fragment.setVisibility(View.INVISIBLE);
                    //Llamamos al callback para que la activity haga lo que juzgue con el alumno
                    escuchador.onGuardar(alumno);
                }
            });
            //Le seteo el botón cancelar y le paso null pq solo queremos que desaparezca el dialogo
            alerta.setNegativeButton(R.string.string_dialogo_cambioalumno_botonCancelar, null);
            //Muestro el diálogo
            alerta.show();
        }
    }

    //Método privado para activar widgets (modificar pulsado)
    private void activarWidgets (){
        editText_nombre.setEnabled(true);
        editText_apellido.setEnabled(true);
        boton_modificar.setEnabled(false);
        boton_guardar.setEnabled(true);
        spinnerCiclo.setEnabled(true);
        radioButtonMujer.setEnabled(true);
        radioButtonHombre.setEnabled(true);
    }
    //Método privado para desactivar widgets (guardar pulsado)
    private void desactivarWidgets (){
        editText_nombre.setEnabled(false);
        editText_apellido.setEnabled(false);
        boton_modificar.setEnabled(true);
        boton_guardar.setEnabled(false);
        spinnerCiclo.setEnabled(false);
        radioButtonMujer.setEnabled(false);
        radioButtonHombre.setEnabled(false);
    }

    //Interface donde publicaremos qué métodos callback debe
    //implementar la actividad que nos contenga.
    public interface FragmentoDetalleListener {
        //Este método se ejecutará cuando se pulse guardar y le pasaremos el alumno ya modificado.
        //Será la actividad la que decida qué hacer con él.
        void onGuardar(Alumno alumno);
    }

    //Con este método permitiremos que la Activity escuche nuestros
    //callbacks
    public void setFragmentoDetalleListener (FragmentoDetalleListener escuchador) {
        this.escuchador = escuchador;
    }
}
