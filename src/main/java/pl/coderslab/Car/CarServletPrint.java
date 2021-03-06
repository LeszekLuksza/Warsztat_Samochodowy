package pl.coderslab.Car;


import pl.coderslab.Customer.Customer;
import pl.coderslab.Customer.CustomerDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/printCars")
public class CarServletPrint extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    //
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Car> cars = null;
        try {
            cars = CarDao.printAllCars();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("/oops.html");
        }


        request.setAttribute("cars", cars);
        getServletContext().getRequestDispatcher("/printvehickles.jsp").forward(request,response);
    }
}
