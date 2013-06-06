package com.vaadin.demo.parking;

import com.vaadin.addon.responsive.Responsive;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.demo.parking.ui.MainTabsheet;
import com.vaadin.demo.parking.util.DataUtil;
import com.vaadin.demo.parking.widgetset.client.model.Ticket;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * The UI class for Parking demo.
 */
@Theme("parking")
@Widgetset("com.vaadin.demo.parking.widgetset.ParkingWidgetset")
@PreserveOnRefresh
@Title("Parking")
public class ParkingUI extends UI {

    /*
     * Default the location to Vaadin HQ
     */
    private double currentLatitude = 60.452541;
    private double currentLongitude = 22.30083;
    private String user;
    private ParkingOfflineModeExtension offlineModeSettings;
    private BeanItemContainer<Ticket> ticketContainer;

    @Override
    public void init(VaadinRequest request) {
        ticketContainer = new BeanItemContainer<Ticket>(Ticket.class,
                DataUtil.generateRandomTickets());
        // Set a nice default for user for demo purposes.
        setUser("John Doe");

        setContent(new MainTabsheet());

        // Use Vornitologists custom offline mode
        offlineModeSettings = new ParkingOfflineModeExtension();
        offlineModeSettings.extend(this);
        offlineModeSettings.setPersistentSessionCookie(true);
        offlineModeSettings.setOfflineModeEnabled(true);

        new Responsive(this);
        setImmediate(true);
    }

    public void goOffline() {
        offlineModeSettings.goOffline();
    }

    /**
     * The location information is stored in Application instance to be
     * available for all components. It is detected by the map view during
     * application init, but also used by other maps in the application.
     * 
     * @return the current latitude as degrees
     */
    public double getCurrentLatitude() {
        return currentLatitude;
    }

    /**
     * @return the current longitude as degrees
     * @see #getCurrentLatitude()
     */
    public double getCurrentLongitude() {
        return currentLongitude;
    }

    /**
     * @see #getCurrentLatitude()
     */
    public void setCurrentLatitude(double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    /**
     * @see #getCurrentLatitude()
     */
    public void setCurrentLongitude(double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    /**
     * A typed version of {@link UI#getCurrent()}
     * 
     * @return the currently active Vornitologist UI.
     */
    public static ParkingUI getApp() {
        return (ParkingUI) UI.getCurrent();
    }

    public static BeanItemContainer<Ticket> getTicketContainer() {
        return getApp().ticketContainer;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
