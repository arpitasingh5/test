package com.example.arpita.expenses;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arpita.expenses.db.ExpenseTable;
import com.example.arpita.expenses.models.Expenses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText name,amt,dateSetBox,searchName;
    TextView date,total,dateSearch;
    FloatingActionButton fab,search,back;
    int i;
    String updname,upddate,sn1;
    Double updAmt;

    int n;
    RelativeLayout ll,ll1,rl2;
    String sd1,sn;
    public static final String TAG="expenses";
    Button add,update,search_by_date,search_by_name;
    ListView listView;
    private DatePickerDialog fromDatePickerDialog,toDatePicker;



    String enterName,enterDate;

    Double enterAmt;

    SimpleDateFormat sd=new SimpleDateFormat("dd-MM-yyyy");

    String date1 = sd.format(new Date());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.design);





        name=(EditText)findViewById(R.id.name);
        ll=(RelativeLayout)findViewById(R.id.ll);
        ll1=(RelativeLayout)findViewById(R.id.ll1);

        rl2=(RelativeLayout)findViewById(R.id.rl2);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        search=(FloatingActionButton)findViewById(R.id.search);
        listView=(ListView) findViewById(R.id.listView);
        back=(FloatingActionButton)findViewById(R.id.back);
        amt=(EditText)findViewById(R.id.amt);
       searchName=(EditText)findViewById(R.id.searchname);
        dateSearch=(TextView)findViewById(R.id.datesearch);
        dateSearch.setText(date1);

        total=(TextView)findViewById(R.id.total);
        search_by_name=(Button)findViewById(R.id.search_name);
        search_by_date=(Button)findViewById(R.id.search_date);

        update=(Button)findViewById(R.id.update);
        date=(TextView) findViewById(R.id.date);
        add=(Button)findViewById(R.id.add);
        date.setText(date1);
        ll.setVisibility(View.INVISIBLE);
        update.setVisibility(View.INVISIBLE);
        rl2.setVisibility(View.INVISIBLE);
        search_by_date.setVisibility(View.INVISIBLE);
        search_by_name.setVisibility(View.INVISIBLE);
        back.setVisibility(View.INVISIBLE);
        SQLiteDatabase myDb=DbOpener.openWriteableDatabase(this);
        showList();


        String[] projection={
                ExpenseTable.Columns.ID,
                ExpenseTable.Columns.NAME,
                ExpenseTable.Columns.AMOUNT,
                ExpenseTable.Columns.DATE
        };


       final Cursor c=myDb.query(
                ExpenseTable.TABLE_NAME,
                projection,null,null,null,null,null
        );
        i=c.getCount();

       search_by_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n=1;

                if(searchName.getText().toString().isEmpty())
                {Toast.makeText(MainActivity.this,"Field cannot be empty",Toast.LENGTH_SHORT).show();

                }
                else{
                sn1 = searchName.getText().toString();

                showList();
                rl2.setVisibility(View.INVISIBLE);
                }

            }
        });
        search_by_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n=2;


                    sd1 = dateSearch.getText().toString();

                    showList();
                    rl2.setVisibility(View.INVISIBLE);


            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n=0;
                showList();
                back.setVisibility(View.INVISIBLE);
                rl2.setVisibility(View.INVISIBLE);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll.setVisibility(View.INVISIBLE);
                back.setVisibility(View.VISIBLE);

               rl2.setVisibility(View.VISIBLE);
                search_by_name.setVisibility(View.VISIBLE);
                search_by_date.setVisibility(View.VISIBLE);

                searchName.setText(null);




            }
        });

       fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            show(v);
                date.setText(date1);
                update.setVisibility(View.INVISIBLE);
                name.setText(null);
                amt.setText(null);
                add.setVisibility(View.VISIBLE);
                rl2.setVisibility(View.INVISIBLE);
                n=0;
                back.setVisibility(View.VISIBLE);

            }
        });

        while(c.moveToNext()) {
            int expID = c.getInt(c.getColumnIndex(ExpenseTable.Columns.ID));
            String expName = c.getString(c.getColumnIndex(ExpenseTable.Columns.NAME));
            String expDate=c.getString(c.getColumnIndex(ExpenseTable.Columns.DATE));
            Double expAmt=c.getDouble(c.getColumnIndex(ExpenseTable.Columns.AMOUNT));
            Log.e(TAG, "onCreate: Student="+expID+" "+expName+" "+expAmt+" "+expDate );


        }


        add.setOnClickListener(new View.OnClickListener() {
            SQLiteDatabase myDb1=DbOpener.openWriteableDatabase(MainActivity.this);


            @Override
            public void onClick(View v) {



                i=i+1;

                if(amt.getText().toString().isEmpty()||name.getText().toString().isEmpty())
                {Toast.makeText(MainActivity.this,"Field cannot be empty",Toast.LENGTH_SHORT).show();

                }
                else {
                    enterAmt = Double.parseDouble(amt.getText().toString());
                    enterName=name.getText().toString();

                        enterDate = date.getText().toString();



                    Expenses exp=new Expenses(
                            enterName,
                            enterAmt,
                            enterDate,
                            i
                    );

                    ContentValues value=new ContentValues();
                    value.put(ExpenseTable.Columns.ID,exp.getId());
                    value.put(ExpenseTable.Columns.NAME,exp.getName());
                    value.put(ExpenseTable.Columns.AMOUNT,exp.getAmount());
                    value.put(ExpenseTable.Columns.DATE,exp.getDate());

                  myDb1.insert(ExpenseTable.TABLE_NAME,null,value);



                    showList();
                    name.setText(null);
                    amt.setText(null);

                }

                back.setVisibility(View.INVISIBLE);
            }


        });




    }




    private class ListAdapter extends BaseAdapter {

        class NameViewHolder{
            TextView NameView;
            TextView AmtView;
            TextView DateView;
        }

        private ArrayList<Expenses> mdetails;

        public ListAdapter(ArrayList<Expenses> mdetails) {

            this.mdetails = mdetails;
        }

        @Override
        public int getCount() {
            return mdetails.size();
        }

        //gives position
        @Override
        public Expenses getItem(int position)
        {
            return mdetails.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater li=getLayoutInflater();
            NameViewHolder nameViewHolder;



            if(convertView == null) {


                    convertView = li.inflate(R.layout.list_item, null);


                nameViewHolder=new NameViewHolder();
                nameViewHolder.NameView=(TextView)convertView.findViewById(R.id.expName);
                nameViewHolder.AmtView=(TextView)convertView.findViewById(R.id.expAmt);
                nameViewHolder.DateView = (TextView) convertView.findViewById(R.id.expDate);
                convertView.setTag(nameViewHolder);
            }else{

                nameViewHolder=(NameViewHolder) convertView.getTag();
            }


           Expenses thisName=getItem(position);

            nameViewHolder.NameView.setText(getItem(position).getName());
            Double amt=(thisName.getAmount());

            nameViewHolder.AmtView.setText("\u20B9"+ Double.parseDouble(String.valueOf(amt)));
            nameViewHolder.DateView.setText("Date: "+getItem(position).getDate());



            return convertView;
        }
    }


    public ArrayList<Expenses> getList() {

        ArrayList<Expenses> expList = new ArrayList<>();
        SQLiteDatabase myDb = DbOpener.openWriteableDatabase(this);



        Cursor c1;
        String tb;

        if(n==1) {

            c1 = myDb.rawQuery("SELECT * FROM " + ExpenseTable.TABLE_NAME + " WHERE " + ExpenseTable.Columns.NAME + " = \"" + sn1 + "\" ; ", null);
                tb = "SELECT SUM(" + ExpenseTable.Columns.AMOUNT + ") FROM " + ExpenseTable.TABLE_NAME + " WHERE " + ExpenseTable.Columns.NAME + " = \"" + sn1 + "\" ; ";
            }
         else if(n==2) {
             Log.e(TAG, "getList:"+sd1+"date");

             c1 = myDb.rawQuery("SELECT * FROM " + ExpenseTable.TABLE_NAME + " WHERE " + ExpenseTable.Columns.DATE + " = \"" + sd1 + "\" ; ", null);
             tb = "SELECT SUM(" + ExpenseTable.Columns.AMOUNT + ") FROM " + ExpenseTable.TABLE_NAME + " WHERE " + ExpenseTable.Columns.DATE + " = \"" + sd1 + "\" ; ";

         }
        else {


            c1 = myDb.rawQuery(ExpenseTable.TABLE_SELECT_ALL, null);
            tb=ExpenseTable.TABLE_ADD;

        }

        if (c1 != null) {
            if (c1.moveToFirst()) {
                do {
                    String expenseName = c1.getString(c1.getColumnIndex(ExpenseTable.Columns.NAME));
                    Double amt = c1.getDouble(c1.getColumnIndex(ExpenseTable.Columns.AMOUNT));
                    String date = c1.getString(c1.getColumnIndex(ExpenseTable.Columns.DATE));


                    int id = c1.getInt(c1.getColumnIndex(ExpenseTable.Columns.ID));

                    Expenses exp1=new Expenses(
                            expenseName,
                            amt,
                            date,
                            id
                    );

                    expList.add(exp1);
                } while (c1.moveToNext());
            }


        }
      Cursor cursor= myDb.rawQuery(tb,null);
            cursor.moveToFirst();

        Double f=cursor.getDouble(0);
        total.setText("Total Expenses: "+f);

        c1.close();

        return expList;

    }

    public void showList(){
        final ArrayList<Expenses > expenseList=getList();
        ll.setVisibility(View.INVISIBLE);

        final ListAdapter nameAdapter;
        nameAdapter = new ListAdapter(expenseList);
        listView.setAdapter(nameAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {



                ll.setVisibility(View.INVISIBLE);
                rl2.setVisibility(View.INVISIBLE);
                back.setVisibility(View.INVISIBLE);


                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setMessage("Expense: "+expenseList.get(position).getName()+"\nDate: "+expenseList.get(position).getDate()+"\nAmount Spent: "+String.valueOf(expenseList.get(position).getAmount()));

                builder1.setCancelable(true);

               final  int k=expenseList.get(position).getId();
                amt.setText(String.valueOf(expenseList.get(position).getAmount()));
                name.setText(expenseList.get(position).getName());
                date.setText(expenseList.get(position).getDate());

                final SQLiteDatabase myDb = DbOpener.openWriteableDatabase(MainActivity.this);

                builder1.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        show(view);
                        add.setVisibility(View.INVISIBLE);
                        update.setVisibility(View.VISIBLE);
                        back.setVisibility(View.VISIBLE);

                        update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                update(k);
                                back.setVisibility(View.INVISIBLE);
                            }
                        });



                    }
                });

                builder1.setPositiveButton(
                        "Done",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder1.setNeutralButton(
                        "Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                             //   Cursor cursor1=  myDb.rawQuery(ExpenseTable.DELETE/*+fg+" ; "*/,null);
                                myDb.delete(ExpenseTable.TABLE_NAME,ExpenseTable.Columns.ID+" = "+k,null);



                                Toast.makeText(MainActivity.this,"Entry Deleted",Toast.LENGTH_SHORT).show();
                                showList();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }






    public void setDateTimeField1(View v) {
        dateSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDatePicker.show();

            }
        });


        Calendar newCalendar = Calendar.getInstance();
        toDatePicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
               int  month=monthOfYear+1;


                   try{
                       String dd=sd.format(sd.parse(dayOfMonth+"-"+month+"-"+year));

                       dateSearch.setText(dd);
                   }
                   catch (ParseException e)
                   {

                   }

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }


    public void setDateTimeField(View v) {
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();

            }
        });


        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                int  month=monthOfYear+1;
                try {


                    date.setText(sd.format(sd.parse(dayOfMonth + "-" + month + "-" + year)));

                }
                catch (ParseException e)
                {

                }

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }


    public void clearAll(View v){

        SQLiteDatabase myDb = DbOpener.openWriteableDatabase(MainActivity.this);
        myDb.delete(ExpenseTable.TABLE_NAME,null,null);
        i=0;
        showList();
        ll.setVisibility(View.INVISIBLE);
        rl2.setVisibility(View.INVISIBLE);
        back.setVisibility(View.INVISIBLE);

    }

    public void update(int k)
    {
        SQLiteDatabase myDb = DbOpener.openWriteableDatabase(MainActivity.this);
        if(amt.getText().toString().isEmpty()||name.getText().toString().isEmpty())
        {Toast.makeText(MainActivity.this,"Field cannot be empty",Toast.LENGTH_SHORT).show();

        }
        else {
            updAmt = Double.parseDouble(amt.getText().toString());
            updname = name.getText().toString();

            upddate = date.getText().toString();

            ContentValues cv = new ContentValues();
            cv.put(ExpenseTable.Columns.NAME,updname);
            cv.put(ExpenseTable.Columns.DATE,upddate);
            cv.put(ExpenseTable.Columns.AMOUNT,updAmt);

            myDb.update(ExpenseTable.TABLE_NAME,cv, ExpenseTable.Columns.ID+" = "+k,null);
            Toast.makeText(MainActivity.this,"Entry Updated",Toast.LENGTH_SHORT).show();

        }
        showList();


    }


    public void show(View v){
        ll.setVisibility(View.VISIBLE);
    }

    public void show1(View v){
        ll1.setVisibility(View.VISIBLE);
    }






}
