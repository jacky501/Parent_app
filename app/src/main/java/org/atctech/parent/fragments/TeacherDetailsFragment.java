package org.atctech.parent.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.atctech.parent.R;
import org.atctech.parent.utils.ConstantValue;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherDetailsFragment extends Fragment {

    private TextView fullName,email,phone,address,details,bdate,blood,sex,paddress;
    private ImageView tproImage;

    public TeacherDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teacher_details, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Teacher Details");

        fullName = view.findViewById(R.id.tFullName);
        email = view.findViewById(R.id.tEmail);
        phone = view.findViewById(R.id.tPhoneNo);
        address = view.findViewById(R.id.tAddress);
        details = view.findViewById(R.id.tDetails);
        bdate = view.findViewById(R.id.tbDate);
        blood = view.findViewById(R.id.tbloodGroup);
        sex = view.findViewById(R.id.tSex);
        paddress = view.findViewById(R.id.tPermanentAddress);
        tproImage = view.findViewById(R.id.t_image);


        Bundle bundle = getArguments();

        String fname = bundle.getString("fname");
        String lname = bundle.getString("lname");
        String temail = bundle.getString("email");
        String tphone = bundle.getString("phone");
        String taddress = bundle.getString("address");
        String tdetails = bundle.getString("details");
        String tbdate = bundle.getString("bdate");
        String tblood = bundle.getString("blood");
        String tsex = bundle.getString("sex");
        String tpaddress = bundle.getString("paddress");
        String tpro_pic = bundle.getString("pro_pic");


        try {
            if (tpro_pic.isEmpty() && tpro_pic.equalsIgnoreCase("") && tpro_pic == null)
            {
                Picasso.with(getContext()).load(R.drawable.profile).into(tproImage);
            }else {
                Picasso.with(getContext()).load(ConstantValue.IMAGE_URL+tpro_pic).placeholder(R.drawable.profile).into(tproImage);
            }
        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        fullName.setText(fname+" "+lname);
        email.setText(temail);
        phone.setText(tphone);
        address.setText(taddress);
        details.setText(tdetails);
        bdate.setText(tbdate);
        blood.setText(tblood);
        sex.setText(tsex);
        paddress.setText(tpaddress);


        return view;
    }

}
