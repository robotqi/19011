import javax.swing.*;
import java.awt.event.*;
import java.util.Date;
/**
 @author Chelsea Dorich (Email: <a href="mailto:"robotqi@gmail.com>robotqi@gmail.com</a>)
 @version 1.1 04/22/2014
 @assignment.number A190-11
 @prgm.usage Called from the operating system
 @see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th Edition"
 @see "<a href='http://docs.oracle.com/javase/7/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>
 */
public class A19011 extends JDialog
{
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox cboLocation;
    private JLabel lblTime;
    private JLabel lblTempC;
    private JLabel lblTempF;
    private JLabel lblRH;
    private JLabel lblDewp;
    private JLabel lblWindDir;
    private JLabel lblWindSp;

    public A19011()
    {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);


        cboLocation.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                getSurfaceWeather(cboLocation.getSelectedItem().toString().substring(0, 4));
            }
        });


// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /**
     * this is a method that uses the database (Weather) to populate the combo box
     * that is called in  the main event
     */
    private void populateLocations()
    { DBUpdt db = new DBUpdt();

        db.openConnection("Weather");
        db.query("SELECT * FROM stations");
        while(db.moreRecords())
        {
            cboLocation.addItem(db.getField("stationid") + " - " + db.getField("city"));
        }
        Date d = new Date();
        lblTime.setText(d.toString());
        lblDewp.setText("");
        lblRH.setText("");
        lblTempC.setText("");
        lblTempF.setText("");
        lblWindDir.setText("");
        lblWindSp.setText("");
    }


    private void onCancel()
    {
// add your code here if necessary
        dispose();
    }

    /**
     * this is a method used in the selected index event that accepts the station ID an
     * uses it to retriev the related information from the web using XML
     * @param strStationId string containing the stations ID
     */
    public void getSurfaceWeather(String strStationId)

        {XMLRead xm = new XMLRead();
            String strUrl = "http://w1.weather.gov/xml/current_obs/"+ strStationId +".xml";
            try
            {
                xm.loadPage(strUrl);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            try
            {
                lblDewp.setText(xm.getField("dewpoint_f"));
                lblRH.setText(xm.getField("relative_humidity"));
                lblTempC.setText(xm.getField("temp_c"));
                lblTempF.setText(xm.getField("temp_f"));
                lblWindDir.setText(xm.getField("wind_dir"));
                lblWindSp.setText(xm.getField("wind_mph"));
            } catch (Exception e)
            {
                e.printStackTrace();
            }


        }

    /**
     * This is the main event that sets up the form and loads the date, time and combo box
     * @param args
     */
    public static void main(String[] args)
    {
        A19011 dialog = new A19011();
        dialog.populateLocations();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
