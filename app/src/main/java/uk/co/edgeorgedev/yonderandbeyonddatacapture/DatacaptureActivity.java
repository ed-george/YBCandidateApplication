package uk.co.edgeorgedev.yonderandbeyonddatacapture;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class DatacaptureActivity extends ActionBarActivity implements AsyncCompleteListener {
    @InjectView(R.id.kbv)
    KenBurnsSupportView kbv;
    @InjectView(R.id.name_edit_text)
    EditText name;
    @InjectView(R.id.phone_edit_text)
    EditText number;
    @InjectView(R.id.email_edit_text)
    EditText email;
    @InjectView(R.id.note_edit_text)
    EditText note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        //Inject views using Butterknife§
        ButterKnife.inject(this);

        //Edit toolbar to display Y&B logo
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.yonder_logo);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Add images to KenBurnsView
        kbv.setResourceIds(R.drawable.underground_angel, R.drawable.yonder_splash, R.drawable.mac_hipster, R.drawable.perth);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.action_about:
                   startActivity(new Intent(this, AboutActivity.class));
                return true;
            case R.id.action_export:
                ExportJsonAsync async = new ExportJsonAsync(this, this);
                async.execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @OnClick(R.id.submit_button)
    protected void submit(){
        //Check if fields are valid
        if(validateFields()) {
            //Create new candidate from entered values
            Candidate candidate = new Candidate();
            candidate.setName(name.getText().toString());
            candidate.setEmail(email.getText().toString());
            candidate.setPhone(number.getText().toString());
            candidate.setNotes(note.getText().toString());

            //Determine whether candidate was saved and display message to user
            boolean success = candidate.create(this);
            showCandidateCreated(success);
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(note.getWindowToken(), 0);
    }

    @OnClick(R.id.clear_button)
    protected void clear(){
        name.setText("");
        number.setText("");
        email.setText("");
        note.setText("");
        name.requestFocus();
    }


    private boolean validateFields() {

        hideKeyboard();

        if(name.getText().toString().equals("") || email.getText().toString().equals("")){
            showValidationError();
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()){
            showEmailValidationError();
            return false;
        }

        return true;
    }

    private void showEmailValidationError() {
        Toast.makeText(this, R.string.enter_valid_email, Toast.LENGTH_SHORT).show();
    }

    private void showValidationError(){
        Toast.makeText(this, R.string.check_valid_details, Toast.LENGTH_SHORT).show();
    }

    private void showCandidateCreated(boolean success){
        Toast.makeText(this, success ? R.string.candidate_added : R.string.candidate_not_added, Toast.LENGTH_LONG).show();
        if(success){ clear(); }
    }

    private void showExportComplete(boolean success){
        Toast.makeText(this, success ? R.string.export_success : R.string.export_failed, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAsyncComplete() {
        showExportComplete(true);
    }

    @Override
    public void onAsyncFailed() {
        showExportComplete(false);
    }
}
