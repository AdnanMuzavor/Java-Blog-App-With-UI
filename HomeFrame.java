package dbmsproject;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADNAN
 */
public class HomeFrame extends Frame implements ActionListener {
    // AUTH CRIDENTAILS

    int userid = -1, currblog = -1;
    String admin = "false";
    String name = "";
    Boolean user_login = false;
    Connection conn;

    // STATEMENT EXECUTOR
    Statement st;

    // GLOBAL VARS TO BE ACCESSED FROM ANYWHERE
    Frame f1; // Main frame where evn menu will be displayed
    TextField namePicker, emailPicker, passwordPicker, t4, addCommentPicker, titlePicker, contentPicker;
    Panel p1, p2;
    Label l1, l2, l3,notify, blog_title, content[], addCommentLbl, titleLabel, contentLabel;
    MenuItem item1, item2, item21;
    MenuBar m1;
    Button submitBtn, resetBtn, addCommentBtn, insertBlogBtn;
    Choice c1;
    Font myFont;
    int n1, n2, blogs_i, blogs_j;
    JTable t1;
    Panel blogs[] = new Panel[100];
    Label texts[] = new Label[100];
    Button btns[] = new Button[100];
    String currblogtitle = "";

    // => CONSTRCTOR TO INITILASE THE BASIC OBJECTS NEEDED EVERYWHERE
    // = >Basically initiialses almost everything
    HomeFrame(Connection c) {
        setTitle("BlOg StAtIoN ApPlIcAtIoN");
        setLayout(new FlowLayout());

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        conn = c;

        try {
            st = conn.createStatement();
        } catch (Exception e) {
            System.out.println("Unble to create stgatement as per: " + e);
        }

        initComponents();
    }

    // => Components Initialiser
    void initComponents() {
        myFont = new Font("Arial", 299, 200);

        // => Acts as notifier to deliver ERROR messages to user
        notify = new Label("I'll notify you about the status");
        notify.setBackground(Color.red);
        notify.setAlignment(FlowLayout.CENTER);
        notify.setForeground(Color.YELLOW);

        // => Initialse the text labels
        l1 = new Label("Enter your name: ");
        l1.setBackground(Color.red);
        l1.setForeground(Color.YELLOW);

        l2 = new Label("Enter email address: ");
        l2.setBackground(Color.red);
        l2.setForeground(Color.YELLOW);

        l3 = new Label("Enter your password: ");
        l3.setBackground(Color.red);
        l3.setForeground(Color.YELLOW);

        // => Initialse the text fields
        namePicker = new TextField("Example: Amit");
        emailPicker = new TextField("Example: Amit@gmail.com");
        passwordPicker = new TextField("Example: @12#24*y");

        // => Initialse the menuBar
        m1 = new MenuBar();
        m1.setFont(new Font("Arial", Font.ITALIC, 10));
        // 1) Create menu1 for USER
        Menu menu1 = new Menu("User");
        menu1.setFont(new Font("Arial", Font.ITALIC, 10));
        Menu menu2 = new Menu("Blog");
        menu2.setFont(new Font("Arial", Font.ITALIC, 10));

        // 2)  Create a menuItems for menu1
        item1 = new MenuItem("Sign Up");
        item2 = new MenuItem("Login");
        item21 = new MenuItem("All Blogs");
        // 3) Add items to menu1
        menu1.add(item1);
        menu1.add(item2);
        menu2.add(item21);
        // 4) Define Action Listeners for each of these Iteams
        item1.addActionListener(this);
        item2.addActionListener(this);
        item21.addActionListener(this);

        // Add menus to the menuBar
        m1.add(menu1);
        m1.add(menu2);
        // Add MenuBar to the screen
        setMenuBar(m1);

        //A  welsome panel
        Panel welcome = new Panel();

        Label welcomeL = new Label("Welcome to BlOg StAtIoN");

        welcomeL.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeL.setBounds(0, 180, 90, 100);
        welcomeL.setForeground(Color.YELLOW);
        welcome.setBackground(Color.red);
        welcome.add(welcomeL);
        add(welcome);

        this.setSize(new Dimension(1500, 500));
        this.setResizable(true);
        this.setBackground(Color.YELLOW);

    }

    public void blogsFetcherAndDisplay() {
        Frame f3 = new Frame("All Blogs");
        f3.setSize(new Dimension(1000, 600));
        f3.setLayout(new FlowLayout());
        // f3.setResizable(false);
        f3.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                f3.setVisible(false);
                notify.setText("I'll notify you the status");
            }
        });
        Panel MainPanelHolder = new Panel();
        MainPanelHolder.setBackground(Color.YELLOW);
        MainPanelHolder.setSize(new Dimension(f3.getWidth() - 100, f3.getHeight() - 100));
        MainPanelHolder.setLayout(new GridLayout(3, 2, 20, 20));
        JScrollPane mainScreenScroller = new JScrollPane(MainPanelHolder);
//        add(mainScreenScroller);
        try {
            ResultSet rs = st.executeQuery("select * from blog");
            // As we have n blogs, well need 1 PANEL, 2 LABELS AND 1 BUTTON FOR EACH
            // blog

            blogs_i = 0;
            blogs_j = 0;
            while (rs.next()) {
                // Fetch temporray details from blog
                int blogId = rs.getInt("blogId");
                String title = rs.getString("title");
                String content = rs.getString("content");
                // Set blog title
                texts[blogs_j] = new Label(title);
                texts[blogs_j].setFont(new Font("Arial", Font.BOLD, 18));
                texts[blogs_j].setForeground(Color.YELLOW);
                texts[blogs_j].setAlignment(FlowLayout.CENTER);

                // Set blog label
                texts[blogs_j + 1] = new Label("Click to view" + "...");
                texts[blogs_j + 1].setFont(new Font("Arial", Font.ITALIC, 15));

                // Set blog button
                btns[blogs_i] = new Button("View blog " + blogId);
                btns[blogs_i].setBackground(Color.YELLOW);
                btns[blogs_i].setForeground(Color.red);
                btns[blogs_i].addActionListener(this);

                // Add these three things on blog panel
                blogs[blogs_i] = new Panel();
                blogs[blogs_i].setLayout(new GridLayout(3, 1, 6, 6));
                blogs[blogs_i].setBackground(Color.red);
                blogs[blogs_i].add(texts[blogs_j]);
                blogs[blogs_i].add(texts[blogs_j + 1]);
                blogs[blogs_i].add(btns[blogs_i]);

                // Add panel to main blog panel
                MainPanelHolder.add(blogs[blogs_i]);

                // increment pointers to load next component
                blogs_i++;
                blogs_j++;
                blogs_j++;

            }
            f3.add(MainPanelHolder);
            f3.setVisible(true);
        } catch (Exception e) {
            System.out.println("Unable to fetch data due to: " + e);
        }
    }

    // Overwriting the ActionPerformed method as per convenience
    public void actionPerformed(ActionEvent ae) {
        String actionCommand = ae.getActionCommand();
        String actions[] = actionCommand.split(" ");
        for (String s : actions) {
            System.out.println(s);
        }
        if (actions[0].equals("View")) {
            actionCommand = actions[0] + " " + actions[1];
            currblog = Integer.parseInt(actions[2]);
            System.out.println("Requested blog: " + currblog);
        }
        System.out.println("Operation requested is: " + actionCommand);
        switch (actionCommand) {
            case "Sign Up":
                frameMaker(actionCommand + " ");
                break;

            case "Login":
                frameMaker(actionCommand + " ");
                break;

            case "LoginC": // => Login after Sign Up
                f1.setVisible(false);
                frameMaker("Login ");
                break;

            case "_Sign Up _": // => Signup Executor
                signUpHandler();
                break;

            case "Logout":
                logoutHandler();
                break;

            case "User":
                launchUserPanel();
                break;
            case "All Blogs":
                blogsFetcherAndDisplay();
                break;
            case "_Login _":
                loginHandler();
                break;

            case "_Reset_":
                namePicker.setText("");
                emailPicker.setText("Example: Amit@gmail.com");
                passwordPicker.setText("Example: @12#24*y");
                notify.setText("I'll notify you the status");
                break;

            case "Delete user":
                launchDeleteUserFrame(actionCommand);
                break;
                
            case "View Status":
                launchViewApplicationStatusFrame(actionCommand);
                break;
            
            case "Total User":
                totalUserPanel("Count Of Total Users");
                break;

            case "Total Blog":
                totalBlogPanel("Total Blogs In Application");
                break;

            case "Blogs by user":
                userBlogPanel("Blogs by each user");
                break;

            case "Delete Blog":
                launchDeleteBlogFrame(actionCommand);
                break;

            case "Insert Blog":
                System.out.println("Requested to insert a blog");
                launchInsertBlogFrame(actionCommand);
                break;

            case "Update Blog":
                launchUpdateBlogFrame(actionCommand);
                break;

            case "Delete Comment":
                launchDeleteCommentFrame(actionCommand);
                break;

            case "Update Profile":
                launchUpdateProfileFrame(actionCommand);
                break;

            case "Update Details":
                UserDataUpdator();
                break;

            case "View blog":
                System.out.println("Requested to view a blog");
                launchBlogScreen(actionCommand);
                break;
            case "View Comments":
                DisplayCommentsFrame();
                break;
            case "Add Comment":
                System.out.println("Requested to add comment");
                launchAddCommentFrame("Add Comment");
                break;

            case "Add Comment on blog":
                addComment();
                break;

            case "Add a blog":
                insertBlog();
                break;
                
            case "UPDATE":
                updateBlog();
                break;

            case "Delete":
                userDeletor();
                break;

            case "_DELETE_":
                blogDeletor();
                break;

            case "DELETE":
                commentDeletor();
                break;

            default:
                System.out.println("No operation chosen");
        }
    }

    //___________________________________________________________________________
    // =============> FUNCTION SECTION <==============================
    //___________________________________________________________________________
    //FUNCTION+SCREEN TO DISPLAY COUNT OF USERS
    public void totalUserPanel(String title) {
        Frame f3 = new Frame(title);
        f3.setSize(new Dimension(300, 400));
        f3.setLayout(new GridLayout(3, 1, 10, 10));
        f3.setResizable(false);
        f3.setTitle(title);

        f3.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                f3.setVisible(false);
                notify.setText("I'll notify you the status");
            }
        });

        Label temp = new Label("Total Users Using the application");
        temp.setAlignment(FlowLayout.CENTER);
        temp.setBackground(Color.YELLOW);
        temp.setForeground(Color.red);
        try {
            // => Get user details from database
            // DefaultTableModel dtm = new DefaultTableModel();
            ResultSet rs = st.executeQuery("select count(userid) as Total_Users_In_Application from user;");

            t1 = new JTable(buildTableModel(rs));
            JScrollPane j1 = new JScrollPane(t1);
            // JOptionPane.showMessageDialog(null,new JScrollPane(t1));
            t1.setBackground(Color.red);
            f3.add(temp);
            f3.add(j1);
            // Make frame visible
            f3.setVisible(true);
        } catch (Exception e) {
            System.out.println("Unable to get details of the user1 due to: " + e);
        }
    }

    //FUNCTION+SCREEN TO DISPLAY COUNT OF TOTAL BLOGS
    public void totalBlogPanel(String title) {
        Frame f3 = new Frame(title);
        f3.setSize(new Dimension(300, 400));
        f3.setLayout(new GridLayout(3, 1, 10, 10));
        f3.setResizable(false);
        f3.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                f3.setVisible(false);
                notify.setText("I'll notify you the status");
            }
        });

        Label temp = new Label("There are these many blogs in our application, woah!!");
        temp.setAlignment(FlowLayout.CENTER);
        temp.setBackground(Color.YELLOW);
        temp.setForeground(Color.red);
        try {
            // => Get user details from database
            // DefaultTableModel dtm = new DefaultTableModel();
            ResultSet rs = st.executeQuery("select count(blogid) as total_blogs from blog");

            t1 = new JTable(buildTableModel(rs));

            JScrollPane j1 = new JScrollPane(t1);
            // JOptionPane.showMessageDialog(null,new JScrollPane(t1));
            t1.setBackground(Color.red);
            f3.add(temp);
            f3.add(j1);
            // Make frame visible
            f3.setVisible(true);
        } catch (Exception e) {
            System.out.println("Unable to get details of the user1 due to: " + e);
        }
    }

    //FUNCTION+SCREEN TO DISPLAY COUNT OF BLOGS BY THAT USER
    public void userBlogPanel(String title) {
        Frame f3 = new Frame(title);
        f3.setSize(new Dimension(300, 400));
        f3.setLayout(new GridLayout(3, 1, 10, 10));
        f3.setResizable(false);
        f3.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                f3.setVisible(false);
                notify.setText("I'll notify you the status");
            }
        });
        Label temp = new Label("Users and Count Of Blogs They Have Written");
        temp.setAlignment(FlowLayout.CENTER);
        temp.setBackground(Color.YELLOW);
        temp.setForeground(Color.red);
        try {
            // => Get user details from database
            // DefaultTableModel dtm = new DefaultTableModel();
            ResultSet rs = st.executeQuery("select u.userid,u.username,count(b.blogid) as blogs_written from user u, blog b where b.userid=u.userid group by u.username,u.userid;");

            t1 = new JTable(buildTableModel(rs));

            JScrollPane j1 = new JScrollPane(t1);
            // JOptionPane.showMessageDialog(null,new JScrollPane(t1));
            t1.setBackground(Color.red);
            f3.add(temp);
            f3.add(j1);
            // Make frame visible
            f3.setVisible(true);
        } catch (Exception e) {
            System.out.println("Unable to get details of the user1 due to: " + e);
        }
    }
    //FUNCTION TO ADD A COMMENT
    public void addComment() {
        System.out.println("Add comment triggered");
        String messege = addCommentPicker.getText();
        int tid = 500;
        int flag = 1;
        while (flag == 1) {
            try {
                String query = "SELECT * FROM COMMENT where cid=" + tid;
                System.out.println(query);
                ResultSet rs = st.executeQuery(query);
                if (rs.next()) {
                    tid++;
                } else {
                    String qr = "INSERT INTO COMMENT VALUES(" + userid + "," + tid + "," + currblog + ",'" + messege + "')";
                    System.out.println(qr);
                    int status = st.executeUpdate(qr);
                    System.out.print("Inserted: " + status + " Comment into DB");
                    flag = 0;
                    notify.setText("Comment Inserted");
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        System.out.println("Comment inserted:" + tid);
    }

    //FUNCTION TO INSERT A BLOG
    public void insertBlog() {
        //remove(MainPanelHolder);
        System.out.println("Add blog triggered");
        String title = titlePicker.getText();
        String content = contentPicker.getText();
        int currblogid = 20;
        int flag = 1;
        while (flag == 1) {
            try {
                String query = "SELECT * FROM BLOG where blogid=" + currblogid;
                System.out.println(query);
                ResultSet rs = st.executeQuery(query);
                if (rs.next()) {
                    currblogid++;
                    continue;
                } else {
                    String qr = "INSERT INTO BLOG VALUES(" + userid + "," + currblogid + ",'" + title + "','" + content + "')";
                    System.out.println(qr);
                    int status = st.executeUpdate(qr);
                    System.out.print("Inserted: " + status + " blog into DB");
                    flag = 0;
                    notify.setText("Blog Inserted");

                    blogsFetcherAndDisplay();

                    break;
                    //add(MainPanelHolder);
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        System.out.println("Blog inserted:" + currblog);
        blogsFetcherAndDisplay();
    }

    // FUNCTION TO CREATE A TABLE
    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }

    // FUNCTION TO DELETE USER USING SELECTED ROW IN TABLE
    public void userDeletor() {
        int row = t1.getSelectedRow();
        String query = "";
        if (row < 0) {
            System.out.println("No row has been selected!!");
            return;
        }
        int delete_id = Integer.parseInt(t1.getValueAt(row, 0).toString());
        System.out.println("Selected: " + delete_id); // get the eid selected

        try {
            // Each user blog shall be deleted, but it's commnet may remain in DB
            // JOIN BLOG tabl with COMMNET table and retrive cids of those commnets
            // which are on blogs written by user who is deleted

            int deleted_recs = st.executeUpdate("delete from comment where cid in"
                    + "(select cid from comment c,blog b where c.blogId=b.blogId and b.userId="
                    + delete_id + ")");

            System.out.println(" => Deleted " + deleted_recs + "comments from DB");

            // => Now delete All blogs of this user
            query = "delete from blog where userId=" + delete_id;
            int status = st.executeUpdate(query);
            System.out.print("Deleted: " + status + " blogs from DB");

            // => Delete All commnets written by this user
            query = "delete from comment where userId=" + delete_id;
            status = st.executeUpdate(query);
            System.out.print("Deleted: " + status + " comments from DB");

            // =>At last delete the user
            query = "delete from user where userId=" + delete_id;
            status = st.executeUpdate(query);

            System.out.println("User deleted from database");
            t1.setValueAt("deleted", row, 0);
            t1.setValueAt("deleted", row, 1);

        } catch (Exception e) {
            System.out.println("Unable to execute query: " + query + " due to: " + e);
        }
    }

    //FUNCTION TO UPDATE BLOG USING SELECTED ROW IN TABLE
    public void updateBlog()
    {
        Frame f3 = new Frame();
        f3.setSize(new Dimension(500, 300));
        f3.setLayout(new FlowLayout());
        f3.setVisible(true);
        f3.setResizable(false);
        f3.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                notify.setText("I'll notify you the status");
                f3.setVisible(false);
            }
        });
        
        int row = t1.getSelectedRow();
        String query = "";
        if (row < 0) {
            System.out.println("No row has been selected!!");
            return;
        }
        int update_blogId = Integer.parseInt(t1.getValueAt(row, 0).toString());
        System.out.println("Selected: " + update_blogId);
        
        try {
            // => Now update blog of the user
            query = "update blog set title='" + titlePicker.getText() + "',content='"
                    + contentPicker.getText() + "' where blogid=" + currblog;
            
            int status = st.executeUpdate(query);
            System.out.print("Updated: " + status + " blogs from DB");

            System.out.println("Blog updated from database");
            t1.setValueAt("updated", row, 0);
            t1.setValueAt("updated", row, 1);
            notify.setText("Blog Updated");

        } catch (Exception e) {
            System.out.println("Unable to execute query: " + query + " due to: " + e);
        }
    }
    
    // FUNCTION TO DELETE BLOG USING SELECTED ROW IN TABLE
    public void blogDeletor() {
        int row = t1.getSelectedRow();
        String query = "";
        if (row < 0) {
            System.out.println("No row has been selected!!");
            return;
        }
        int delete_blogId = Integer.parseInt(t1.getValueAt(row, 0).toString());
        System.out.println("Selected: " + delete_blogId);

        try {
            // => Now delete blog of the user
            query = "delete from blog where blogId=" + delete_blogId;
            int status = st.executeUpdate(query);
            System.out.print("Deleted: " + status + " blogs from DB");

            // => Delete All commnets written on this blog
            query = "delete from comment where blogId=" + delete_blogId;
            status = st.executeUpdate(query);
            System.out.print("Deleted: " + status + " comments from DB");

            System.out.println("Blog deleted from database");
            t1.setValueAt("deleted", row, 0);
            t1.setValueAt("deleted", row, 1);
            notify.setText("Blog Deleted");

        } catch (Exception e) {
            System.out.println("Unable to execute query: " + query + " due to: " + e);
        }
    }

    // FUNCTION TO DELETE COMMENT USING SELECTED ROW IN TABLE
    public void commentDeletor() {
        int row = t1.getSelectedRow();
        String query = "";
        if (row < 0) {
            System.out.println("No row has been selected!!");
            return;
        }
        int delete_commentId = Integer.parseInt(t1.getValueAt(row, 0).toString());
        System.out.println("Selected: " + delete_commentId);

        try {
            // => Delete the commnet written on the blog
            query = "delete from comment where cid = " + delete_commentId;
            int status = st.executeUpdate(query);
            System.out.print("Deleted: " + status + " comment from DB");

            System.out.println("Comment deleted from database");
            t1.setValueAt("deleted", row, 0);
            t1.setValueAt("deleted", row, 1);
            notify.setText("Comment Deleted");
        } catch (Exception e) {
            System.out.println("Unable to execute query: " + query + " due to: " + e);
        }
    }

    // FUNCTION TO UPDATE USER DATA
    public void UserDataUpdator() {
        String query = "";
        try {
            System.out.println("Updating User Details");
            query = "update user set email='" + emailPicker.getText() + "',pw='"
                    + passwordPicker.getText() + "',username='" + namePicker.getText()
                    + "' where userid=" + userid;
            System.out.println("Executing query: " + query);
            int res = st.executeUpdate(query);
            System.out.println(res + " Updated");
            notify.setText("Data UPDATED!!");

        } catch (Exception e) {
            System.out.println("Unable to execute  " + query + " due to: " + e);
        }
    }

    // FUNCTION TO HANDLE SIGNUP
    public void signUpHandler() {
        try {
            Boolean user_id_exists = true;
            Boolean user_email_exists = false;
            int user_id = 300;

            // => Firts find if this email id was not used before
            ResultSet rs = st.executeQuery(
                    "select * from user where email='" + emailPicker.getText() + "'");
            if (rs.next()) {
                user_email_exists = true;
            }

            // => If only email is uniqye[DOESNT EXISTS] go heahd with this loop, else
            // it'll break
            // => Generate user_id that deosmnt exists
            while (user_id_exists && !user_email_exists) {
                // Generate random user id for the user
                user_id = (int) Math.floor(Math.random() * 1000 + 100);

                System.out.println("User id generated is: " + user_id);

                // Search if this user id exists in database
                // => Firts find if this email id was not used before
                rs = st.executeQuery("select * from user where userid=" + user_id);
                if (!rs.next()) {
                    user_id_exists = false;
                }
            }

            if (!user_email_exists) {
                String query = "insert into user values(" + user_id + ",'"
                        + emailPicker.getText() + "','" + passwordPicker.getText() + "','"
                        + namePicker.getText() + "','false')";
                System.out.println("Request to EXECUTE: " + query);
                int insert = st.executeUpdate(query);
                System.out.println(query + " EXECUTED");
                notify.setText("User signup successfully");
                namePicker.setText("");
                emailPicker.setText("");
                passwordPicker.setText("");
                f1.remove(p1);
                p2.remove(resetBtn);
                submitBtn.setLabel("LoginC");
                submitBtn.setActionCommand("LoginC");
                // f1.add(notify);

            } else {
                System.out.println("Draw apprpriate message!!");
                if (user_email_exists) {
                    notify.setText("Email address exists");
                    System.out.println("Email address exists");
                }
            }
        } catch (Exception e) {
            System.out.println("Unable to insert record into DB as " + e);
        }
    }

    public void loginHandler() {
        try {
            // Check if user exists or not by matching password and email fields
            user_login = false;
            // => Firts find if this email id was not used before
            ResultSet rs = st.executeQuery("select * from user where email='"
                    + emailPicker.getText() + "' and pw='" + passwordPicker.getText()
                    + "'");
            if (rs.next()) {
                user_login = true;
            }

            if (!user_login) {
                System.out.println("User login failed!! : " + emailPicker.getText()
                        + " : " + passwordPicker.getText());
                notify.setText("Invalid cridentials");
                user_login = false;

            } else {
                System.out.println("User login Done!!");
                notify.setText("User logged in");
                userid = rs.getInt("userid");
                admin = rs.getString("admin");
                name = rs.getString("username");
                System.out.println(
                        "User id is: " + userid + " user id admin? => " + admin);

                launchUserPanel();

                f1.removeAll();
                f1.add(notify);
                user_login = true;
                namePicker.setText("");
                emailPicker.setText("");
                passwordPicker.setText("");
                item2.setLabel("Logout");
                item1.setLabel("User");
            }
        } catch (Exception e) {
            System.out.println("Unable to insert record into DB as " + e);
        }
    }

    public void logoutHandler() {
        userid = -1;
        name = "";
        admin = "false";
        user_login = false;
        item2.setLabel("Login");
        item1.setLabel("Sign Up");
    }
    //___________________________________________________________________________
    // =============> FRAME SECTION <==============================
    //___________________________________________________________________________

    // FUNCTION TO LAUNCH DELETE USER UI
    public void launchDeleteUserFrame(String title) {
        Panel p = new Panel();
        Button deleteUserBtn = new Button("Delete");
        deleteUserBtn.addActionListener(this);
        deleteUserBtn.setSize(100, 20);
        p.add(deleteUserBtn);
        deleteUserBtn.setBackground(Color.YELLOW);
        deleteUserBtn.setForeground(Color.red);

        Label temp = new Label("Select user you want to delete");
        temp.setAlignment(FlowLayout.CENTER);
        temp.setBackground(Color.YELLOW);
        temp.setForeground(Color.red);

        Frame f3 = new Frame(title);
        f3.setSize(new Dimension(300, 400));
        f3.setLayout(new GridLayout(3, 1, 10, 10));
        // f3.setResizable(false);
        f3.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                f3.setVisible(false);
                notify.setText("I'll notify you the status");
            }
        });
        try {
            // => Gte user details from database
            // DefaultTableModel dtm = new DefaultTableModel();
            ResultSet rs = st.executeQuery("select userid,username from user;");

            t1 = new JTable(buildTableModel(rs));
            JScrollPane j1 = new JScrollPane(t1);
            // JOptionPane.showMessageDialog(null,new JScrollPane(t1));
            t1.setBackground(Color.red);
            f3.add(temp);
            f3.add(j1);
            f3.add(p);
            ;
            // Make frame vissible
            f3.setVisible(true);
        } catch (Exception e) {
            System.out.println("Unable to get details of the user1 due to: " + e);
        }
    }
    
    // FUNCTION TO LAUNCH APPLICATION STATUS
    public void launchViewApplicationStatusFrame(String title) 
    {
        Panel p = new Panel();
        Button totalUserBtn = new Button("Total User");
        totalUserBtn.addActionListener(this);
        totalUserBtn.setSize(100, 20);
        p.add(totalUserBtn);
        totalUserBtn.setBackground(Color.YELLOW);
        totalUserBtn.setForeground(Color.red);

        Button totalBlogBtn = new Button("Total Blog");
        totalBlogBtn.addActionListener(this);
        totalBlogBtn.setSize(100, 20);
        p.add(totalBlogBtn);
        totalBlogBtn.setBackground(Color.YELLOW);
        totalBlogBtn.setForeground(Color.red);

        Button userBlogBtn = new Button("Blogs by user");
        userBlogBtn.addActionListener(this);
        userBlogBtn.setSize(100, 20);
        p.add(userBlogBtn);
        userBlogBtn.setBackground(Color.YELLOW);
        userBlogBtn.setForeground(Color.red);

        Label temp = new Label("Hey, " + name + "Select the details you want");
        temp.setAlignment(FlowLayout.CENTER);
        temp.setBackground(Color.YELLOW);
        temp.setForeground(Color.red);

        Frame f3 = new Frame(title);
        f3.setSize(new Dimension(300, 400));
        f3.setLayout(new GridLayout(3, 1, 10, 10));
        f3.setResizable(false);
        f3.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                f3.setVisible(false);
                notify.setText("I'll notify you the status");
            }
        });
        f3.add(temp);
        f3.add(p);

        f3.setVisible(true);
    }   

    //Screen to view a single blog
    public void launchBlogScreen(String title) {
        Frame f3 = new Frame(title);

        f3.setSize(new Dimension(400, 600));

        f3.setLayout(new GridLayout(4, 1));
        f3.setResizable(false);
        f3.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                f3.setVisible(false);
                notify.setText("I'll notify you the status");
            }
        });
        Panel content_panel = new Panel();
        content_panel.setSize(new Dimension(500, 100));
        content_panel.setBackground(Color.YELLOW);
        //A select query 
        try {

            //initialising labels
            blog_title = new Label("First Blog");
            content = new Label[30];
            for (int i = 0; i < 30; i++) {
                content[i] = new Label("Hello ");
            }
            int content_i = 0; //content ietrator
            //content = new Label("hj6unhu7j  j7yn 7juj8");

            String query = "select * from blog where blogId=" + currblog;
            System.out.println(query);
            ResultSet rs = st.executeQuery(query);

            ///Variable to contain the content
            String blog_content = "";
            int flag = 1;

            //get the content
            while (rs.next()) {
                blog_content = rs.getString("content");
                blog_title.setText(rs.getString("title"));
                currblogtitle = rs.getString("title");
            }
            System.out.println("Blog content: " + blog_content);

            //Break content into 10 chars
            //and insert inside the panel
            while (blog_content.split(" ").length >= 10) {

                String strs[] = blog_content.split(" ");
                String curr_line = "";
                int trim_len = 0;
                for (int i = 0; i < 10; i++) {
                    //Get current 10 words
                    curr_line += (strs[i] + " ");
                    //add to length so as to remove the length later
                    trim_len += (strs[i].length() + 1);
                }
                System.out.println("CUURENT LINE: " + curr_line);
                content[content_i].setText(curr_line);
                content_panel.add(content[content_i]);
                content_i++;
                blog_content = blog_content.substring(trim_len);
                System.out.println("Updated blog content is: " + blog_content);
            }
            System.out.println("OUT OF LOOP: Updated blog content is: " + blog_content);

            content[content_i].setText(blog_content);
            content_panel.add(content[content_i]);
            content_i++;

        } catch (Exception e) {
            System.out.println("Unable to display due to :" + e);
        }

        Panel Title_panel = new Panel();
        Title_panel.setSize(new Dimension(300, 10));
        Title_panel.add(blog_title);
        Title_panel.setBackground(Color.red);
        f3.add(Title_panel);

        //content_panel.add(content);
        f3.add(content_panel);

        Button view_comment = new Button("View Comments " + currblog);
        view_comment.setSize(new Dimension(30, 15));
        view_comment.setBackground(Color.YELLOW);
        view_comment.setForeground(Color.red);

        view_comment.addActionListener(this);

        Label view_comment_label = new Label("Hit this button to view all comments");
        Panel view_comment_panel = new Panel();
        view_comment_panel.setLayout(new FlowLayout());
        view_comment_panel.setBackground(Color.red);
        view_comment_panel.setSize(new Dimension(100, 30));
        view_comment_panel.add(view_comment_label);
        view_comment_panel.add(view_comment);

        if (user_login == true) {
            Button add_comment = new Button("Add Comment");
            add_comment.setSize(new Dimension(30, 15));
            add_comment.setBackground(Color.YELLOW);
            add_comment.setForeground(Color.red);
            add_comment.addActionListener(this);
            Label comment_label = new Label("Hit this button to add the comment");
            view_comment_panel.add(comment_label);
            view_comment_panel.add(add_comment);

        }
        f3.add(view_comment_panel);

        // Make frame vissible
        f3.setVisible(true);
    }

    // FUNCTION TO UPDATE USER PROFILE
    public void launchUpdateProfileFrame(String title) {
        Frame f3 = new Frame(title);
        f3.setSize(new Dimension(400, 180));
        f3.setLayout(new FlowLayout());
        f3.setResizable(false);
        f3.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                f3.setVisible(false);
                notify.setText("I'll notify you the status");
            }
        });

        try {
            // => Gte user details from database
            ResultSet rs
                    = st.executeQuery("select * from user where userid=" + userid);
            if (rs.next()) {
                System.out.println("Got result set");
                notify.setText("I'll notify you the status");
            }

            // => Set text fields with user details
            emailPicker.setText(rs.getString("email"));
            passwordPicker.setText(rs.getString("pw"));
            namePicker.setText(rs.getString("username"));

            // =>  Show text boxes on the frame
            p1 = new Panel();
            p1.setLayout(new GridLayout(3, 2, 10, 10));

            // => Adding elements of firts panel
            p1.add(l1);
            p1.add(namePicker);
            p1.add(l2);
            p1.add(emailPicker);
            p1.add(l3);
            p1.add(passwordPicker);

            // Make second panel only for buttons
            p2 = new Panel();
            p2.setLayout(new GridLayout(1, 1, 20, 20));
            submitBtn = new Button("Update Details");
            submitBtn.addActionListener(this);
            p2.add(submitBtn);

            // Add panels to the frame
            f3.add(notify);
            f3.add(p1);
            f3.add(p2);

            // Make frame vissible
            f3.setVisible(true);
        } catch (Exception e) {
            System.out.println("Unable to get details of the user1 due to: " + e);
        }
    }

    // FUNCTION TO INSERT BLOG
    public void launchInsertBlogFrame(String title) {
        Frame f3 = new Frame(title);
        f3.setSize(new Dimension(350, 180));
        f3.setLayout(new GridLayout(3, 2, 10, 10));
        f3.setVisible(true);
        f3.setResizable(false);

        titleLabel = new Label("Enter the title");
        titlePicker = new TextField("MY BLOG");
        contentLabel = new Label("Enter the content");
        contentPicker = new TextField("MY FIRST BLOG");
        insertBlogBtn = new Button("Add a blog");

        f3.add(titleLabel);
        f3.add(titlePicker);
        f3.add(contentLabel);
        f3.add(contentPicker);
        f3.add(insertBlogBtn);
        f3.add(notify);
        insertBlogBtn.addActionListener(this);
        f3.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                f3.setVisible(false);
                notify.setText("I'll notify you the status");
            }
        });
    }

    // FUNCTION TO UPDATE BLOG
    public void launchUpdateBlogFrame(String title) 
    {
        Panel p = new Panel();
        Button updateBlogBtn = new Button("UPDATE");
        updateBlogBtn.addActionListener(this);
        updateBlogBtn.setSize(100, 20);
        p.add(updateBlogBtn);
        updateBlogBtn.setBackground(Color.YELLOW);
        updateBlogBtn.setForeground(Color.red);

        Label temp = new Label("Select the blog you want to update");
        temp.setAlignment(FlowLayout.CENTER);
        temp.setBackground(Color.YELLOW);
        temp.setForeground(Color.red);

        Frame f3 = new Frame(title);
        f3.setSize(new Dimension(500, 300));
        f3.setLayout(new FlowLayout());
        f3.setVisible(true);
        f3.setResizable(false);
        f3.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                notify.setText("I'll notify you the status");
                f3.setVisible(false);
            }
        });
        try {
            // => Get blog details from database
            // DefaultTableModel dtm = new DefaultTableModel();
            ResultSet rs = st.executeQuery("select blogId,title from blog where blogId in (select blogId from blog where userid = " + userid + ")");

            t1 = new JTable(buildTableModel(rs));
            JScrollPane j1 = new JScrollPane(t1);
            // JOptionPane.showMessageDialog(null,new JScrollPane(t1));
            t1.setBackground(Color.red);
            
            f3.add(temp);
            f3.add(notify);
            f3.add(p);
            f3.add(j1);

            // Make frame vissible
            f3.setVisible(true);
        } catch (Exception e) {
            System.out.println("Unable to get details of the blog due to: " + e);
        }
    }
    
    //FUNCTION TO DELETE BLOG
    public void launchDeleteBlogFrame(String title) {
        Panel p = new Panel();
        Button deleteBlogBtn = new Button("_DELETE_");
        deleteBlogBtn.addActionListener(this);
        deleteBlogBtn.setSize(100, 20);
        p.add(deleteBlogBtn);
        deleteBlogBtn.setBackground(Color.YELLOW);
        deleteBlogBtn.setForeground(Color.red);

        Label temp = new Label("Select the blog you want to delete");
        temp.setAlignment(FlowLayout.CENTER);
        temp.setBackground(Color.YELLOW);
        temp.setForeground(Color.red);

        Frame f3 = new Frame(title);
        f3.setSize(new Dimension(500, 300));
        f3.setLayout(new FlowLayout());
        f3.setVisible(true);
        f3.setResizable(false);
        f3.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                notify.setText("I'll notify you the status");
                f3.setVisible(false);
            }
        });
        try {
            // => Get blog details from database
            // DefaultTableModel dtm = new DefaultTableModel();
            ResultSet rs = st.executeQuery("select blogId,title from blog where blogId in (select blogId from blog where userid = " + userid + ")");

            t1 = new JTable(buildTableModel(rs));
            JScrollPane j1 = new JScrollPane(t1);
            // JOptionPane.showMessageDialog(null,new JScrollPane(t1));
            t1.setBackground(Color.red);
            f3.add(temp);
            f3.add(notify);
            f3.add(p);
            f3.add(j1);

            // Make frame vissible
            f3.setVisible(true);
        } catch (Exception e) {
            System.out.println("Unable to get details of the blog due to: " + e);
        }
    }

    // FUNCTION TO ADD A COMMENT
    public void launchAddCommentFrame(String title) {
        Frame f3 = new Frame(title);
        f3.setSize(new Dimension(350, 200));
        f3.setLayout(new GridLayout(3, 2, 10, 10));
        f3.setVisible(true);
        f3.setResizable(false);
        addCommentPicker = new TextField("Nice Blog");
        addCommentBtn = new Button("Add Comment on blog");
        addCommentLbl = new Label("Enter the Comment");
        f3.add(addCommentLbl);
        f3.add(addCommentPicker);
        f3.add(addCommentBtn);
        f3.add(notify);
        addCommentBtn.addActionListener(this);
        f3.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                notify.setText("I'll notify you the status");
                f3.setVisible(false);
            }
        });
    }

    //FUNCTION TO DELETE A COMMENT
    public void launchDeleteCommentFrame(String title) {
        Panel p = new Panel();
        Button deleteCommentBtn = new Button("DELETE");
        deleteCommentBtn.addActionListener(this);
        deleteCommentBtn.setSize(100, 20);
        p.add(deleteCommentBtn);
        deleteCommentBtn.setBackground(Color.YELLOW);
        deleteCommentBtn.setForeground(Color.red);

        Label temp = new Label("Select the comment you want to delete");
        temp.setAlignment(FlowLayout.CENTER);
        temp.setBackground(Color.YELLOW);
        temp.setForeground(Color.red);

        Frame f3 = new Frame(title);
        f3.setSize(new Dimension(500, 500));
        f3.setLayout(new FlowLayout());
        f3.setVisible(true);
        f3.setResizable(false);
        f3.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                notify.setText("I'll notify you the status");
                f3.setVisible(false);
            }
        });
        try {
            // => Get comment details from database
            // DefaultTableModel dtm = new DefaultTableModel();
            ResultSet rs = st.executeQuery("select c.cid,c.blogid,c.message,b.title from comment c,blog b where c.blogid = b.blogid and c.userid = " + userid);

            t1 = new JTable(buildTableModel(rs));
            JScrollPane j1 = new JScrollPane(t1);
            // JOptionPane.showMessageDialog(null,new JScrollPane(t1));
            t1.setBackground(Color.red);
            f3.add(temp);
            f3.add(notify);
            f3.add(p);
            f3.add(j1);

            // Make frame vissible
            f3.setVisible(true);
        } catch (Exception e) {
            System.out.println("Unable to get details of the comment due to: " + e);
        }
    }

    public void launchUserPanel() {
        System.out.println("Launch Admin Panel");
        // => Create Frame
        Frame f3
                = new Frame(admin.equals(true) ? "Hello_Admin_" + name : "Hello_" + name);
        f3.setSize(new Dimension(400, 200));
        f3.setLayout(new FlowLayout());

        // => Create panel inside the frame
        Panel MainPanelHolder2 = new Panel();
        MainPanelHolder2.setLayout(new GridLayout(3, 3, 10, 10));

        // => Create buttons to be inside the frame
        Button delUser;
        Button viewStatus;
        Button delBlog = new Button("Delete Blog");
        delBlog.setBackground(Color.YELLOW);
        delBlog.setForeground(Color.red);
        delBlog.addActionListener(this);

        Button insertBlog = new Button("Insert Blog");
        insertBlog.setBackground(Color.YELLOW);
        insertBlog.setForeground(Color.red);
        insertBlog.addActionListener(this);

        Button updateBlog = new Button("Update Blog");
        updateBlog.setBackground(Color.YELLOW);
        updateBlog.setForeground(Color.red);
        updateBlog.addActionListener(this);

        Button delComment = new Button("Delete Comment");
        delComment.setBackground(Color.YELLOW);
        delComment.setForeground(Color.red);
        delComment.addActionListener(this);

        Button updateProfile = new Button("Update Profile");
        updateProfile.setBackground(Color.YELLOW);
        updateProfile.setForeground(Color.red);
        updateProfile.addActionListener(this);

        // Add button sto the frame
        MainPanelHolder2.add(insertBlog);
        MainPanelHolder2.add(updateBlog);
        MainPanelHolder2.add(delBlog);
        MainPanelHolder2.add(delComment);
        MainPanelHolder2.add(updateProfile);

        // => Rendering delete user [Only FOR ADMIN]
        if (admin.equals("true")) {
            delUser = new Button("Delete user");
            delUser.setBackground(Color.YELLOW);
            delUser.setForeground(Color.red);
            delUser.addActionListener(this);
            MainPanelHolder2.add(delUser);
            
            viewStatus = new Button("View Status");
            viewStatus.setBackground(Color.YELLOW);
            viewStatus.setForeground(Color.red);
            viewStatus.addActionListener(this);
            MainPanelHolder2.add(viewStatus);

        }

        // => Add panel to frame and make it visisble
        f3.add(MainPanelHolder2);
        f3.setVisible(true);
        f3.setResizable(false);
        f3.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                f3.setVisible(false);
                notify.setText("I'll notify you the status");
            }
        });
    }

    // FUNCTION TO MAKE LOOGIN/SIGNUP FRAME ONLY
    public void frameMaker(String actionstr) {
        // => Same message will be shown in submit button with resp suffix
        submitBtn = new Button("_" + actionstr + "_");
        resetBtn = new Button("_Reset_");

        //__________________FRAME_START_______________________________________________
        // => Create a new frame and set it's attributes
        f1 = new Frame();

        f1.setLayout(new FlowLayout()); // => Let this frame as a flowLayout
        f1.setTitle(actionstr); // => Setting title of frame based on its operation
        f1.setVisible(true); // => Make frame visible
        f1.setSize(new Dimension(
                400, 180)); // => Shall be decide dbased on frame screen type
        f1.setResizable(
                false); // => So that user cannot change it's size(TO PROTECT ALIGNMENT)
        notify.setText("I'll notify you the status");
        f1.add(notify);
        //__________________FRAME_END_______________________________________________
        //__________________PANEL_START_______________________________________________
        // => The first panel will be for text boxes [ONLY]
        p1 = new Panel();

        // => Decide hwich textboxes are to be showjn based on screen
        if (actionstr.trim().equals("Sign Up")) {
            // => It shall have 3 rows and 2 columns and larger size
            p1.setLayout(new GridLayout(3, 2, 10, 10));

            // => Adding elements of firts panel
            p1.add(l1);
            p1.add(namePicker);
            p1.add(l2);
            p1.add(emailPicker);
            p1.add(l3);
            p1.add(passwordPicker);

        } else {
            // => It shall have 2 rows and 2 columns and smaller size
            p1.setLayout(new GridLayout(2, 2, 20, 20));
            f1.setSize(new Dimension(400, 160));
            // => Adding elements of firts panel
            p1.add(l2);
            p1.add(emailPicker);
            p1.add(l3);
            p1.add(passwordPicker);
        }

        // Add first panel to the frame
        f1.add(p1);

        // Make second panel only for buttons
        p2 = new Panel();
        p2.setLayout(new GridLayout(1, 2, 20, 20));
        p2.add(submitBtn);
        p2.add(resetBtn);

        // => Add second panel to the frame
        f1.add(p2);

        // Window listener for closing the window
        f1.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                notify.setText("I'll notify you the status");
                f1.setVisible(false);
            }
        });

        submitBtn.addActionListener(this);
        resetBtn.addActionListener(this);
    }

    //Frame to display comments on specific blog
    void DisplayCommentsFrame() {
        Frame f3 = new Frame("All comments");

        f3.setSize(new Dimension(400, 600));

        f3.setLayout(new GridLayout(4, 1));
        f3.setResizable(false);
        f3.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                f3.setVisible(false);
                notify.setText("I'll notify you the status");
            }
        });
        Panel title_panel = new Panel();
        Label title_label = new Label("All comments on blog: " + currblogtitle);
        title_panel.add(title_label);
        title_panel.setSize(new Dimension(400, 50));
        title_label.setAlignment(FlowLayout.CENTER);
        title_panel.setBackground(Color.lightGray);
        title_panel.setForeground(Color.red);
        title_panel.setFont(new Font("Arial", Font.BOLD, 20));
        f3.add(title_panel);
        try {

            //Comment view Code 
            //
            
            //fetch all comments
            String get_query = "select username,message from comment_view where blogid=" + currblog;
            System.out.println(get_query);
            ResultSet r2 = st.executeQuery(get_query);

            Panel MainPanelHolder2 = new Panel();
            MainPanelHolder2.setBackground(Color.DARK_GRAY);
            MainPanelHolder2.setSize(new Dimension(500, 100));
            MainPanelHolder2.setLayout(new GridLayout(5, 1, 20, 20));

            Panel blogs[] = new Panel[100];
            Label texts[] = new Label[100];

            int i = 0, j = 0,flag=0;
            while (r2.next()) {

                // Fetch temporray details from blog
                String username = r2.getString("username");  //title- >username
                String message = r2.getString("message"); //content->message

                System.out.println("Making label 1");
                // Set blog title
                texts[j] = new Label(username);
                texts[j].setFont(new Font("Arial", Font.BOLD, 18));
                texts[j].setForeground(Color.YELLOW);
                texts[j].setAlignment(FlowLayout.CENTER);

                // Set blog label
                System.out.println("Making label 2");
                texts[j + 1] = new Label("'" + message + "'");
                texts[j + 1].setFont(new Font("Arial", Font.ITALIC, 15));

                System.out.println("Making panel");
                // Add these three things on blog panel
                blogs[i] = new Panel();
                blogs[i].setSize(200, 600);
                blogs[i].setLayout(new GridLayout(1, 2, 6, 6));
                blogs[i].setBackground(Color.red);
                blogs[i].add(texts[j]);
                blogs[i].add(texts[j + 1]);

                System.out.println("Adding label");
                // Add panel to main blog panel
                MainPanelHolder2.add(blogs[i]);

                // increment pointers to load next component
                i++;
                j++;
                j++;
                flag=1;
            }
            //If flag==0 => No commenst so display message:
            if(flag==0){
                title_label.setText("Blog: "+currblogtitle+" has no comments");
            }
            else{
                f3.add(MainPanelHolder2);
            }
            
            f3.setVisible(true);
        } catch (Exception e) {
            System.out.println("Unable to fetch comments in blog " + currblog + " due to: " + e);
        }
    }
}