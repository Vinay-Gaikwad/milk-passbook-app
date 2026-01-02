package com.shiv.milkpassbook;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.view.View;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class BillDetailActivity extends AppCompatActivity {

    private String firmName, partyName, partyCode, date, time, shift, milkType, liter, fat, snf, rate, total;
    private TextView tvFirmName, tvDate, tvTime, tvShift, tvCustomerNo, tvMilk, tvFatSnf, tvRateTotal, tvFooter;
    private String fontName = "mangal.ttf";
    private boolean isSharePdf = false;

    private ActivityResultLauncher<Intent> createFileLauncher;
    private LinearLayout layoutBill;
    private ProgressBar cardLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);

        layoutBill = findViewById(R.id.layoutBill);
        cardLoading = findViewById(R.id.cardLoading);

        layoutBill.setVisibility(View.GONE);
        cardLoading.setVisibility(View.VISIBLE);

        new Handler().postDelayed(() -> {
            findViewById(R.id.loadingContainer).setVisibility(View.GONE);  // Hide loading container
            findViewById(R.id.layoutBill).setVisibility(View.VISIBLE);     // Show actual bill
        }, 2000);


        tvFirmName = findViewById(R.id.tvFirmName);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        tvShift = findViewById(R.id.tvShift);
        tvCustomerNo = findViewById(R.id.tvCustomerNo);
        tvMilk = findViewById(R.id.tvMilk);
        tvFatSnf = findViewById(R.id.tvFatSnf);
        tvRateTotal = findViewById(R.id.tvRateTotal);
        tvFooter = findViewById(R.id.tvFooter);

        firmName = getIntent().getStringExtra("firmName");
        partyName = getIntent().getStringExtra("partyName");
        partyCode = getIntent().getStringExtra("partyCode");
        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("time");
        shift = getIntent().getStringExtra("shift");
        milkType = getIntent().getStringExtra("milkType");
        liter = getIntent().getStringExtra("liter");
        fat = getIntent().getStringExtra("fat");
        snf = getIntent().getStringExtra("snf");
        rate = getIntent().getStringExtra("rate");
        total = getIntent().getStringExtra("total");

        loadData();

        createFileLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Uri uri = result.getData().getData();
                if (uri != null) {
                    createAndSavePdf(uri);
                }
            }
        });

        findViewById(R.id.btnPrint).setOnClickListener(v -> {
            isSharePdf = false;
            askUserLocation();
        });

        findViewById(R.id.btnSharePdf).setOnClickListener(v -> {
            isSharePdf = true;
            createAndSharePdfDirectly();
        });

        LinearLayout btnHomeContainer = findViewById(R.id.btnHomeContainer);
        LinearLayout btnViewCustomersContainer = findViewById(R.id.btnViewCustomersContainer);

        btnHomeContainer.setOnClickListener(v -> {
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
            finish();
        });

        btnViewCustomersContainer.setOnClickListener(v -> {
            Intent intent = new Intent(BillDetailActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void loadData() {
        try {
            Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/" + fontName);
            tvFirmName.setTypeface(typeface);
            tvDate.setTypeface(typeface);
            tvTime.setTypeface(typeface);
            tvShift.setTypeface(typeface);
            tvCustomerNo.setTypeface(typeface);
            tvMilk.setTypeface(typeface);
            tvFatSnf.setTypeface(typeface);
            tvRateTotal.setTypeface(typeface);
            tvFooter.setTypeface(typeface);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tvFirmName.setText(firmName);
        tvDate.setText("दिनांक : " + date);
        tvTime.setText("वेळ : " + time);
        tvShift.setText("शिफ्ट : " + shift);
        tvCustomerNo.setText("ग्राहक : " + partyCode + " " + partyName);
        tvMilk.setText("दूध : " + liter + " " + milkType);
        tvFatSnf.setText("फॅट: " + fat + "    SNF: " + snf);
        tvRateTotal.setText("दर : ₹" + rate + "    रकम : ₹" + total);
        tvFooter.setText("धन्यवाद!");
    }

    private void askUserLocation() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_TITLE, "bill.pdf");
        createFileLauncher.launch(intent);
    }

    private void createAndSavePdf(Uri uri) {
        try {
            PdfDocument pdfDocument = buildPdf();
            OutputStream outputStream = getContentResolver().openOutputStream(uri);
            pdfDocument.writeTo(outputStream);
            outputStream.close();
            pdfDocument.close();
            Toast.makeText(this, "PDF saved successfully.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void createAndSharePdfDirectly() {
        try {
            File pdfFile = new File(getCacheDir(), "bill_share.pdf");
            FileOutputStream outputStream = new FileOutputStream(pdfFile);

            PdfDocument pdfDocument = buildPdf();
            pdfDocument.writeTo(outputStream);
            outputStream.close();
            pdfDocument.close();

            Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", pdfFile);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("application/pdf");
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, "Share Bill PDF"));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Share failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private PdfDocument buildPdf() {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        paint.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL));
        paint.setTextSize(10);

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(144, 144, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        int centerX = 72;
        int y = 15;

        centerText(canvas, firmName, centerX, y, paint); y += 13;
        leftText(canvas, "दिनांक: " + date, 10, y, paint); y += 11;
        leftText(canvas, "वेळ: " + time, 10, y, paint); y += 11;
        leftText(canvas, "शिफ्ट: " + shift, 10, y, paint); y += 11;
        leftText(canvas, "ग्राहक: " + partyCode + " " + partyName, 10, y, paint); y += 11;
        leftText(canvas, "दूध: " + liter + " " + milkType, 10, y, paint); y += 11;
        leftText(canvas, "फॅट: " + fat + "    SNF: " + snf, 10, y, paint); y += 11;
        leftText(canvas, "दर: ₹" + rate + "  रकम: ₹" + total, 10, y, paint); y += 14;
        centerText(canvas, "धन्यवाद!", centerX, y, paint);

        pdfDocument.finishPage(page);
        return pdfDocument;
    }

    private void centerText(Canvas canvas, String text, int centerX, int y, Paint paint) {
        float textWidth = paint.measureText(text);
        canvas.drawText(text, centerX - (textWidth / 2), y, paint);
    }

    private void leftText(Canvas canvas, String text, int x, int y, Paint paint) {
        canvas.drawText(text, x, y, paint);
    }
}
