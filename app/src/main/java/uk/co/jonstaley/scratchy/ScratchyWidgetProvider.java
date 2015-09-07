package uk.co.jonstaley.scratchy;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by jon on 07/09/15.
 */
public class ScratchyWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        try{
            updateWidgetContents(context, appWidgetManager);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void updateWidgetContents(Context context, AppWidgetManager appWidgetManager){
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.note_widget);
        remoteView.setTextViewText(R.id.textView, getContent(context));

        Intent launchAppIntent = new Intent(context, MainActivity.class);
        PendingIntent launchAppPendingIntent = PendingIntent.getActivity(context,
                0, launchAppIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setOnClickPendingIntent(R.id.textView, launchAppPendingIntent);

        ComponentName scratchyWidget = new ComponentName(context, ScratchyWidgetProvider.class);
        appWidgetManager.updateAppWidget(scratchyWidget, remoteView);
    }

    protected static String getContent(Context context){
        File file = new File(context.getFilesDir(), "scratchy");

        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return text.toString();
    }
}
