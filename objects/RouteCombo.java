/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;

/**
 *
 * @author Adam Nogradi
 */
public class RouteCombo {
    private final Route route;
    public RouteCombo(Route inputRoute)
    {
        route = inputRoute;
    }
    public Route getRoute()
    {
        return route;
    }
    @Override
    public String toString()
    {
        return route.getName();
    }
}
