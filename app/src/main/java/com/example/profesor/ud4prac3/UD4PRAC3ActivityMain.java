package com.example.profesor.ud4prac3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.profesor.ud4prac3.Alumnos.Alumno;
import com.example.profesor.ud4prac3.Fragmentos.FragmentoDetalle;
import com.example.profesor.ud4prac3.Fragmentos.FragmentoMenu;

import java.util.ArrayList;


public class UD4PRAC3ActivityMain extends AppCompatActivity implements FragmentoMenu.FragmentoMenuListener, FragmentoDetalle.FragmentoDetalleListener {

    //Claves que usaremos para comunicarnos con otras actividades
    public static final String KEY_REQUEST = "alumno";
    public static final String KEY_RESULT = "result";
    //Códigos de petición y resultado para startActivityForResult() y onActivityResult()
    public static final int CODE_RESULT_OK = 1;
    public static final int CODE_REQUEST_ALUMNO=0;
    private FragmentoMenu fragmentoMenu;
    private FragmentoDetalle fragmentoDetalle;
    private ArrayList<Alumno> arrayAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Iniciamos los textview desde el layout y los invisibilizamos
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ud3_prac2_activity_main);
        //Creamos una lista de alumnos nueva (normalmente la recuperaríamos de una BD persistente)
        arrayAlumno = Alumno.generaListaAlumnos();
        //Instanciamos el fragmentoMenu desde el layout de la actividad
        //para después erigirnos como oyentes de sus callback.
        fragmentoMenu = (FragmentoMenu)getFragmentManager().findFragmentById(R.id.fragment_menu);
        fragmentoMenu.setFragmentoMenuListener(this);
        //Le pasamos la lista de alumnos para que la muestre en un listView
        fragmentoMenu.setAlumnos(arrayAlumno);
        //Comprobamos si existe el Fragmento Detalle (Tablet) o no (Smartphone). Si existe
        //nos erigimos en oyentes del fragmento. Deberemos implementar para ello la interfaz
        //FragmentoDetalle.FragmentoDetalleListener
        fragmentoDetalle = (FragmentoDetalle)getFragmentManager().findFragmentById(R.id.fragment_detalle);
        if(fragmentoDetalle!=null){
            fragmentoDetalle.setFragmentoDetalleListener(this);
        }
    }

    //Implementamos los métodos que expone el fragmentoMenu para poder responder a sus
    //callbacks.
    @Override
    public void onAlumnoPulsado(Alumno alumno) {
        if(fragmentoDetalle!=null){
            fragmentoDetalle.setAlumno(alumno);
        }
        else {
            llamaActividad(alumno);
        }
    }

    //Este método privado se encargará de llamar a la actividad secundaria en caso de ejecutarse en un
    //smarphone. Le pasará a la actividad el tipo de dato que queremos
    private void llamaActividad (Alumno alumno){
        //Creamos un intent y le asociamos un bundle que habremos creado con el alumno
        Intent intent = new Intent(this,UD4PRAC3ActivityAux.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_REQUEST, alumno);
        //Le enviamos en el intent el alumno que mostrar/modificar
        intent.putExtras(bundle);
        //Esta llamada a actividad secundaria hará que al devolvernos el control se ejecute onAcivityResult
        startActivityForResult(intent, CODE_REQUEST_ALUMNO);
    }

    //Se ejecutará cuando la actividad secundaria termine con lo suyo
    // y nos devuelva el control y un resultado
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Si hemos recibido un resultCode == 1 es que todo va bien
        if (resultCode == CODE_RESULT_OK) {
            //Obtenemos el alumno del Bundle
            Bundle extras = data.getExtras();
            Alumno alumno = extras.getParcelable(KEY_RESULT);
            modificaMenu(alumno);
        }
        else {
            // Rutinas en caso de error
        }
    }

    //Implementamos el método onGuardar que nos expone la interface del FragmentoDetalle
    //El parámetro será un alumno modificado
    @Override
    public void onGuardar(final Alumno alumno) {
        modificaMenu(alumno);
    }

    public void modificaMenu (Alumno alumno){
        //Modificamos el array de alumnos
        arrayAlumno.set(alumno.getPosicion(),alumno);
        //Le pasamos al fragmentoMenu el alumno modificado para que lo actualice en el listView
        fragmentoMenu.modificaAlumnoEnMenu(alumno);
        //Mostramos en un TOAST el nombre y apellido del alumno.
        Toast.makeText(this, "Se modificó " + alumno.toString(), Toast.LENGTH_LONG).show();
    }
}
