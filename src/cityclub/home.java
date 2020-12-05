
package cityclub;


import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class home extends javax.swing.JFrame {

    /**
     * Creates new form home
     * @throws java.text.ParseException
     */
    public home() throws ParseException {
        initComponents();
        affichage_abonnement_en_cours() ;
        abonnement_termine() ;
        historique();
        reglementMember() ;
        impayes() ;
        this.setLocationRelativeTo(this);
    }

    public Connection getConnection(){
        Connection con ;
        try{
            con= (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3324/gym", "root", "");
            return con ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Member> memberList(){
        ArrayList<Member> memberList = new ArrayList<Member> ();
        Connection connection = getConnection();
        
        String query = "SELECT * FROM `member` " ;
        Statement st;
        ResultSet rs ;
        try {
            st = connection.createStatement();
            rs =st.executeQuery(query);
            Member member ;
            //Member(int id ,String nomM ,String prenomM ,String dateNaissanceM ,String sexeM ,String emailM,String numéroM, String sportM ,double montantPayé ,String dateDebutM ,String typeabonnement)
            while (rs.next()){
                member = new Member(rs.getInt("idm"),rs.getString("nomM"),rs.getString("prenomM"),rs.getString("dateNM"), rs.getString("sexe"),rs.getString("emailM"),rs.getString("numéroM"),rs.getString("sportM"),rs.getString("dateDebutM") ,rs.getInt("typeabonn"),rs.getString("dateFinM"));
               //public Member(int id ,String nomM ,String prenomM ,String dateNaissanceM ,String sexeM ,String emailM,String numéroM, String sportM ,String dateDebutM ,int typeabonnement,String dateFinM)
                memberList.add(member);
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
        return memberList ;
    }
    
    // table des abonnement en cours
    public void affichage_abonnement_en_cours() throws ParseException{
        ArrayList<Member> list = memberList();
        DefaultTableModel model = (DefaultTableModel) jTable_abonnementsencours.getModel();
        Object[] row =new Object [5];
        for (int i =0 ;i<list.size();i++ )
       {
           if (list.get(i).isEndABon()){
                row[0]=list.get(i).jourRest();
                row[1]=list.get(i).getPrenomM();
                row[2]=list.get(i).getNomM();
                row[3]=list.get(i).getDateDebutM();
                row[4]=list.get(i).getDateFinMM();
                model.addRow(row);
           }
       }
    }
    public void abonnement_termine() throws ParseException
    {
        ArrayList<Member> list = memberList();
        DefaultTableModel model = (DefaultTableModel) jTable_abonnementtermine.getModel();
        Object[] row =new Object[4] ;
        for (int i =0 ;i<list.size();i++ )
        {
          if (!list.get(i).isEndABon()){
              row[0]=list.get(i).getPrenomM();
              row[1]=list.get(i).getNomM() ;
              row[2]=list.get(i).getDateDebutM();
              row[3]=list.get(i).getDateFinMM();
              model.addRow(row);
           }
          
        }
     }
    
    
  
     public void impayes(){       
        
        DefaultTableModel model = (DefaultTableModel) table_historique.getModel();
        Object row[] =new Object[9];
        Connection connection = getConnection();
        int i =0 ;
        String query = "SELECT `nomM`, `prenomM`, `sportM`, `typeabonn`,`datePay`, `montantPayé`,'restPayé','recus' FROM `member`,`payement`" ;
        Statement st=null ;
        ResultSet rs ;
        try {
            st = connection.createStatement();
            rs =st.executeQuery(query);
            while (rs.next()){
                    row[0]=rs.getDouble("restPayé");
                    row[1]=rs.getDouble("montantPayé");
                    row[2]=rs.getString("prenomM");
                    row[3]=rs.getString("nomM");
                    row[4]=rs.getString("sportM");
                    row[5]=rs.getInt("typeabonn");
                    row[6]=prixAbonnement(rs.getInt("typeabonn"));
                    row[7]=rs.getString("datePay");
                    row[8]=rs.getDouble("restPayé");
                    row[9]=rs.getDouble("recus");
                    model.addRow(row);
                
            }
            
       }catch (Exception e){
            e.printStackTrace();
       }
     }
     
     public void historique(){       
        
        DefaultTableModel model = (DefaultTableModel) table_historique.getModel();
        Object row[] =new Object[6];
        Connection connection = getConnection();
        int i =0 ;
        String query = "SELECT `nomM`, `prenomM`, `sportM`, `typeabonn`,`datePay`, `montantPayé` FROM `member`,`payement`" ;
        Statement st=null ;
        ResultSet rs ;
        try {
            st = connection.createStatement();
            rs =st.executeQuery(query);
            while (rs.next()){
                    row[0]=rs.getString("nomM");
                    row[1]=rs.getString("prenomM");
                    row[2]=rs.getString("sportM");
                    row[3]=rs.getInt("typeabonn");
                    row[4]=rs.getString("datePay");
                    row[5]=rs.getDouble("montantPayé");
                    model.addRow(row);
                    i++;
                
            }
            
       }catch (Exception e){
            e.printStackTrace();
       }
     }
    
    //panel de reglement 
    public void reglementMember() throws ParseException
    {
        ArrayList<Member> list = memberList();
        DefaultTableModel model = (DefaultTableModel) tbl_memberReg.getModel();
        Object[] row =new Object[3] ;
        for (int i =0 ;i<list.size();i++ )
        {
              row[0]=list.get(i).getId();
              row[1]=list.get(i).getPrenomM();
              row[2]=list.get(i).getNomM() ;
              model.addRow(row);          
        }
     }
     public void reglementP(int id){       
        
        DefaultTableModel model = (DefaultTableModel) tbl_regelementP.getModel();
        Object row[] =new Object[3];
        Connection connection = getConnection();
        int i =0 ;
        String query = "SELECT `datePay`, `montantPayé` FROM ,`payement` WHERE idm ="+id ;
        Statement st=null ;
        ResultSet rs ;
        try {
            st = connection.createStatement();
            rs =st.executeQuery(query);
            while (rs.next()){
                    row[0]=id;
                    row[1]=rs.getString("datePay");
                    row[2]=rs.getDouble("montantPayé");
                    model.addRow(row);
                
            }
            
       }catch (Exception e){
            e.printStackTrace();
       }
     }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btn_Exit = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel26 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        btn_reglements = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        btn_historique = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        btn_regler = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        btn_abonnemetencours = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        btn_abonnementtermines = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        btn_lesimpayés = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        panelrechercher = new javax.swing.JPanel();
        rbtn_nompredat = new javax.swing.JRadioButton();
        txt_nompredat = new javax.swing.JTextField();
        rbtn_ID = new javax.swing.JRadioButton();
        txt_ID = new javax.swing.JTextField();
        rbt_sports = new javax.swing.JRadioButton();
        cb_sport = new javax.swing.JComboBox<>();
        rbtn_homme = new javax.swing.JRadioButton();
        rbtn_femme = new javax.swing.JRadioButton();
        jLabel47 = new javax.swing.JLabel();
        btn_recherche = new javax.swing.JLabel();
        req = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_abonnementsencours = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        table_historique = new javax.swing.JTable();
        jLabel56 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jpanel_rechregler = new javax.swing.JPanel();
        rbtn_nompredatregler = new javax.swing.JRadioButton();
        txt_nompredatregler = new javax.swing.JTextField();
        rbtn_IDregler = new javax.swing.JRadioButton();
        txt_IDregler = new javax.swing.JTextField();
        rbt_sportsregler = new javax.swing.JRadioButton();
        cb_spoetregler = new javax.swing.JComboBox<>();
        rbtn_hommeregler = new javax.swing.JRadioButton();
        rbtn_femmeregler = new javax.swing.JRadioButton();
        alb_sexeregler = new javax.swing.JLabel();
        btn_rechercheregler = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable_abonnementtermine = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        nb_impayés = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        msg_renouvler1 = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jpanel_rech = new javax.swing.JPanel();
        rbtn_nompredatAT = new javax.swing.JRadioButton();
        txt_nompredatAT = new javax.swing.JTextField();
        rbtn_IDAT = new javax.swing.JRadioButton();
        txt_IDAT = new javax.swing.JTextField();
        rbt_sportsAT = new javax.swing.JRadioButton();
        cb_spoetAT = new javax.swing.JComboBox<>();
        rbtn_hommeAT = new javax.swing.JRadioButton();
        rbtn_femmeAT = new javax.swing.JRadioButton();
        alb_sexeAT = new javax.swing.JLabel();
        btn_rechercheAT = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_impayé = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        nb_abonnementTR = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        msg_renouvler = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_memberReg = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl_regelementP = new javax.swing.JTable();
        jpanel_rech1 = new javax.swing.JPanel();
        rbtn_nompredatREG = new javax.swing.JRadioButton();
        txt_nompredatREG = new javax.swing.JTextField();
        rbtn_IDREG = new javax.swing.JRadioButton();
        txt_IDREG = new javax.swing.JTextField();
        rbt_sportsREG = new javax.swing.JRadioButton();
        cb_spoetREG = new javax.swing.JComboBox<>();
        rbtn_hommeREG = new javax.swing.JRadioButton();
        rbtn_femmeREG = new javax.swing.JRadioButton();
        alb_sexeREG = new javax.swing.JLabel();
        btn_rechercheREG = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btn_members = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btn_abonnement = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btn_paiments = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btn_consultation = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        btn_statistiques = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btn_statistiques1 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setForeground(new java.awt.Color(102, 255, 102));
        jPanel1.setMaximumSize(new java.awt.Dimension(1076, 32767));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(1248, 625));
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel1MousePressed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Microsoft JhengHei UI Light", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("City Club");

        btn_Exit.setBackground(new java.awt.Color(0, 0, 0));
        btn_Exit.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        btn_Exit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_Exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_proj/cancel (1).png"))); // NOI18N
        btn_Exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ExitMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 12, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
            .addComponent(btn_Exit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 204, 204));
        jPanel4.setPreferredSize(new java.awt.Dimension(1199, 670));

        jTabbedPane1.setBackground(new java.awt.Color(242, 237, 237));

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel49.setText("Members");

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(370, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(266, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab1", jPanel26);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel3.setText("Abonnement");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(370, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(266, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab2", jPanel5);

        jPanel7.setBackground(new java.awt.Color(242, 237, 237));

        jLabel4.setBackground(new java.awt.Color(228, 67, 13));
        jLabel4.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 48)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Paiments");

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_proj/folder (1).png"))); // NOI18N

        btn_reglements.setBackground(new java.awt.Color(255, 255, 255));
        btn_reglements.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(228, 67, 13)));
        btn_reglements.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_reglementsMouseClicked(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(228, 67, 13));
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("Regelemnts");

        javax.swing.GroupLayout btn_reglementsLayout = new javax.swing.GroupLayout(btn_reglements);
        btn_reglements.setLayout(btn_reglementsLayout);
        btn_reglementsLayout.setHorizontalGroup(
            btn_reglementsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_reglementsLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );
        btn_reglementsLayout.setVerticalGroup(
            btn_reglementsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_reglementsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel45)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(btn_reglements, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btn_reglements, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_proj/online-payment.png"))); // NOI18N

        btn_historique.setBackground(new java.awt.Color(255, 255, 255));
        btn_historique.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(228, 67, 13)));
        btn_historique.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_historiqueMouseClicked(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(228, 67, 13));
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setText("Historique");

        javax.swing.GroupLayout btn_historiqueLayout = new javax.swing.GroupLayout(btn_historique);
        btn_historique.setLayout(btn_historiqueLayout);
        btn_historiqueLayout.setHorizontalGroup(
            btn_historiqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_historiqueLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );
        btn_historiqueLayout.setVerticalGroup(
            btn_historiqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_historiqueLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel46)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(btn_historique, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btn_historique, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_proj/payment.png"))); // NOI18N

        btn_regler.setBackground(new java.awt.Color(255, 255, 255));
        btn_regler.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(228, 67, 13)));
        btn_regler.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_reglerMouseClicked(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(228, 67, 13));
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("Régler");

        javax.swing.GroupLayout btn_reglerLayout = new javax.swing.GroupLayout(btn_regler);
        btn_regler.setLayout(btn_reglerLayout);
        btn_reglerLayout.setHorizontalGroup(
            btn_reglerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_reglerLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );
        btn_reglerLayout.setVerticalGroup(
            btn_reglerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_reglerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel44)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(btn_regler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btn_regler, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(337, 337, 337)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(43, 43, 43))
        );

        jTabbedPane1.addTab("Paiments", jPanel7);

        jPanel8.setBackground(new java.awt.Color(242, 237, 237));

        jLabel15.setBackground(new java.awt.Color(255, 51, 0));
        jLabel15.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 48)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(102, 102, 102));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Consultation");

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setForeground(new java.awt.Color(255, 255, 255));

        btn_abonnemetencours.setBackground(new java.awt.Color(255, 255, 255));
        btn_abonnemetencours.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(228, 67, 13)));
        btn_abonnemetencours.setForeground(new java.awt.Color(228, 67, 13));
        btn_abonnemetencours.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_abonnemetencoursMouseClicked(evt);
            }
        });
        btn_abonnemetencours.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_abonnemetencoursKeyPressed(evt);
            }
        });

        jLabel18.setBackground(new java.awt.Color(228, 67, 13));
        jLabel18.setFont(new java.awt.Font("Microsoft YaHei Light", 0, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(228, 67, 13));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Abonnement en cours");

        javax.swing.GroupLayout btn_abonnemetencoursLayout = new javax.swing.GroupLayout(btn_abonnemetencours);
        btn_abonnemetencours.setLayout(btn_abonnemetencoursLayout);
        btn_abonnemetencoursLayout.setHorizontalGroup(
            btn_abonnemetencoursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_abonnemetencoursLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addContainerGap())
        );
        btn_abonnemetencoursLayout.setVerticalGroup(
            btn_abonnemetencoursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_abonnemetencoursLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addContainerGap())
        );

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_proj/responsibility (3).png"))); // NOI18N

        jLabel19.setBackground(new java.awt.Color(228, 67, 13));
        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(228, 67, 13));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel19.setText("° ");
        jLabel19.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel20.setBackground(new java.awt.Color(228, 67, 13));
        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(228, 67, 13));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel20.setText("° ");
        jLabel20.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel21.setBackground(new java.awt.Color(228, 67, 13));
        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(228, 67, 13));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel21.setText("° ");
        jLabel21.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel22.setFont(new java.awt.Font("Microsoft YaHei Light", 0, 12)); // NOI18N
        jLabel22.setText("Identité");

        jLabel23.setFont(new java.awt.Font("Microsoft YaHei Light", 0, 12)); // NOI18N
        jLabel23.setText("Jous restants");

        jLabel24.setFont(new java.awt.Font("Microsoft YaHei Light", 0, 12)); // NOI18N
        jLabel24.setText("Date debut/fin");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_abonnemetencours, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addComponent(btn_abonnemetencours, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setForeground(new java.awt.Color(255, 255, 255));

        btn_abonnementtermines.setBackground(new java.awt.Color(255, 255, 255));
        btn_abonnementtermines.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(228, 67, 13)));
        btn_abonnementtermines.setForeground(new java.awt.Color(228, 67, 13));
        btn_abonnementtermines.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_abonnementterminesMouseClicked(evt);
            }
        });

        jLabel25.setBackground(new java.awt.Color(228, 67, 13));
        jLabel25.setFont(new java.awt.Font("Microsoft YaHei Light", 0, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(228, 67, 13));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Abonnement termines");

        javax.swing.GroupLayout btn_abonnementterminesLayout = new javax.swing.GroupLayout(btn_abonnementtermines);
        btn_abonnementtermines.setLayout(btn_abonnementterminesLayout);
        btn_abonnementterminesLayout.setHorizontalGroup(
            btn_abonnementterminesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_abonnementterminesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                .addContainerGap())
        );
        btn_abonnementterminesLayout.setVerticalGroup(
            btn_abonnementterminesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_abonnementterminesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addContainerGap())
        );

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_proj/fast (1).png"))); // NOI18N

        jLabel27.setBackground(new java.awt.Color(228, 67, 13));
        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(228, 67, 13));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel27.setText("° ");
        jLabel27.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel28.setBackground(new java.awt.Color(228, 67, 13));
        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(228, 67, 13));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel28.setText("° ");
        jLabel28.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel30.setFont(new java.awt.Font("Microsoft YaHei Light", 0, 12)); // NOI18N
        jLabel30.setText("Identité");

        jLabel31.setFont(new java.awt.Font("Microsoft YaHei Light", 0, 12)); // NOI18N
        jLabel31.setText("Date debut/fin");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_abonnementtermines, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addComponent(btn_abonnementtermines, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setForeground(new java.awt.Color(255, 255, 255));

        btn_lesimpayés.setBackground(new java.awt.Color(255, 255, 255));
        btn_lesimpayés.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(228, 67, 13)));
        btn_lesimpayés.setForeground(new java.awt.Color(228, 67, 13));
        btn_lesimpayés.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_lesimpayésMouseClicked(evt);
            }
        });

        jLabel33.setBackground(new java.awt.Color(228, 67, 13));
        jLabel33.setFont(new java.awt.Font("Microsoft YaHei Light", 0, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(228, 67, 13));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("Les impayés");

        javax.swing.GroupLayout btn_lesimpayésLayout = new javax.swing.GroupLayout(btn_lesimpayés);
        btn_lesimpayés.setLayout(btn_lesimpayésLayout);
        btn_lesimpayésLayout.setHorizontalGroup(
            btn_lesimpayésLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_lesimpayésLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addContainerGap())
        );
        btn_lesimpayésLayout.setVerticalGroup(
            btn_lesimpayésLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_lesimpayésLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel33)
                .addContainerGap())
        );

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_proj/payment-method (1).png"))); // NOI18N

        jLabel35.setBackground(new java.awt.Color(228, 67, 13));
        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(228, 67, 13));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel35.setText("° ");
        jLabel35.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel36.setBackground(new java.awt.Color(228, 67, 13));
        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(228, 67, 13));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel36.setText("° ");
        jLabel36.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel37.setBackground(new java.awt.Color(228, 67, 13));
        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(228, 67, 13));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel37.setText("° ");
        jLabel37.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel38.setFont(new java.awt.Font("Microsoft YaHei Light", 0, 12)); // NOI18N
        jLabel38.setText("Identité");

        jLabel39.setFont(new java.awt.Font("Microsoft YaHei Light", 0, 12)); // NOI18N
        jLabel39.setText("Doit payer");

        jLabel40.setFont(new java.awt.Font("Microsoft YaHei Light", 0, 12)); // NOI18N
        jLabel40.setText("Date debut/fin");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_lesimpayés, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel39))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addComponent(btn_lesimpayés, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(311, 311, 311))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46))
        );

        jTabbedPane1.addTab("Consultation", jPanel8);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel16.setText("Statistiques");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(370, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(266, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab5", jPanel9);

        jPanel10.setBackground(new java.awt.Color(242, 237, 237));

        panelrechercher.setBackground(new java.awt.Color(255, 255, 255));
        panelrechercher.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelrechercher.setForeground(new java.awt.Color(255, 51, 0));

        rbtn_nompredat.setBackground(new java.awt.Color(255, 255, 255));
        rbtn_nompredat.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 12)); // NOI18N
        rbtn_nompredat.setText("Nom/prenom/Date Naissance");
        rbtn_nompredat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_nompredatActionPerformed(evt);
            }
        });

        rbtn_ID.setBackground(new java.awt.Color(255, 255, 255));
        rbtn_ID.setFont(new java.awt.Font("Microsoft JhengHei UI Light", 0, 12)); // NOI18N
        rbtn_ID.setText("ID");

        txt_ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_IDActionPerformed(evt);
            }
        });

        rbt_sports.setBackground(new java.awt.Color(255, 255, 255));
        rbt_sports.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 12)); // NOI18N
        rbt_sports.setText("Sport");

        cb_sport.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 11)); // NOI18N
        cb_sport.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "GYM", "KARATE", "PICINE" }));

        rbtn_homme.setBackground(new java.awt.Color(255, 255, 255));
        rbtn_homme.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 12)); // NOI18N
        rbtn_homme.setText("Homme");

        rbtn_femme.setBackground(new java.awt.Color(255, 255, 255));
        rbtn_femme.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 12)); // NOI18N
        rbtn_femme.setText("Femme");

        jLabel47.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 14)); // NOI18N
        jLabel47.setText("Sexe :");

        btn_recherche.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_recherche.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_proj/magnifying-glass.png"))); // NOI18N
        btn_recherche.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_rechercheMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelrechercherLayout = new javax.swing.GroupLayout(panelrechercher);
        panelrechercher.setLayout(panelrechercherLayout);
        panelrechercherLayout.setHorizontalGroup(
            panelrechercherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelrechercherLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtn_nompredat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_nompredat, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtn_ID)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbt_sports)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cb_sport, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtn_homme)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtn_femme, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_recherche)
                .addGap(73, 73, 73))
        );
        panelrechercherLayout.setVerticalGroup(
            panelrechercherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelrechercherLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelrechercherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_recherche)
                    .addGroup(panelrechercherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rbtn_nompredat)
                        .addComponent(txt_nompredat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rbtn_ID)
                        .addComponent(txt_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rbt_sports)
                        .addComponent(cb_sport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rbtn_homme)
                        .addComponent(rbtn_femme)
                        .addComponent(jLabel47)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        req.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        req.setText("requete");

        jTable_abonnementsencours.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "JOUR RESTANTS", "PRENOM", "NOM", "DATE DEBUT", "DATE FIN"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_abonnementsencours.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(jTable_abonnementsencours);
        jTable_abonnementsencours.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(252, 252, 252)
                        .addComponent(req, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addComponent(panelrechercher, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(panelrechercher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(req, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Abonnements en cours", jPanel10);

        jPanel12.setBackground(new java.awt.Color(242, 237, 237));

        table_historique.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Prenom", "Nom", "Sport", "Abonnement(mois)", "Date payement", "Montant payé"
            }
        ));
        jScrollPane4.setViewportView(table_historique);

        jLabel56.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 24)); // NOI18N
        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel56.setText("HISTORIQUE");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(403, Short.MAX_VALUE)
                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(398, 398, 398))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel56)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Historique", jPanel12);

        jPanel24.setBackground(new java.awt.Color(242, 237, 237));
        jPanel24.setPreferredSize(new java.awt.Dimension(1376, 670));

        jpanel_rechregler.setBackground(new java.awt.Color(255, 255, 255));
        jpanel_rechregler.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jpanel_rechregler.setForeground(new java.awt.Color(255, 51, 0));

        rbtn_nompredatregler.setBackground(new java.awt.Color(255, 255, 255));
        rbtn_nompredatregler.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 12)); // NOI18N
        rbtn_nompredatregler.setText("Nom/prenom/Date Naissance");
        rbtn_nompredatregler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_nompredatreglerActionPerformed(evt);
            }
        });

        rbtn_IDregler.setBackground(new java.awt.Color(255, 255, 255));
        rbtn_IDregler.setFont(new java.awt.Font("Microsoft JhengHei UI Light", 0, 12)); // NOI18N
        rbtn_IDregler.setText("ID");

        txt_IDregler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_IDreglerActionPerformed(evt);
            }
        });

        rbt_sportsregler.setBackground(new java.awt.Color(255, 255, 255));
        rbt_sportsregler.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 12)); // NOI18N
        rbt_sportsregler.setText("Sport");

        cb_spoetregler.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 11)); // NOI18N
        cb_spoetregler.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "GYM", "KARATE", "PICINE" }));

        rbtn_hommeregler.setBackground(new java.awt.Color(255, 255, 255));
        rbtn_hommeregler.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 12)); // NOI18N
        rbtn_hommeregler.setText("Homme");

        rbtn_femmeregler.setBackground(new java.awt.Color(255, 255, 255));
        rbtn_femmeregler.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 12)); // NOI18N
        rbtn_femmeregler.setText("Femme");

        alb_sexeregler.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 14)); // NOI18N
        alb_sexeregler.setText("Sexe :");

        btn_rechercheregler.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_rechercheregler.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_proj/magnifying-glass.png"))); // NOI18N
        btn_rechercheregler.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_recherchereglerMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpanel_rechreglerLayout = new javax.swing.GroupLayout(jpanel_rechregler);
        jpanel_rechregler.setLayout(jpanel_rechreglerLayout);
        jpanel_rechreglerLayout.setHorizontalGroup(
            jpanel_rechreglerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel_rechreglerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtn_nompredatregler)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_nompredatregler, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtn_IDregler)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_IDregler, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbt_sportsregler)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cb_spoetregler, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(alb_sexeregler, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtn_hommeregler)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtn_femmeregler, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_rechercheregler)
                .addGap(73, 73, 73))
        );
        jpanel_rechreglerLayout.setVerticalGroup(
            jpanel_rechreglerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel_rechreglerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanel_rechreglerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_rechercheregler)
                    .addGroup(jpanel_rechreglerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rbtn_nompredatregler)
                        .addComponent(txt_nompredatregler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rbtn_IDregler)
                        .addComponent(txt_IDregler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rbt_sportsregler)
                        .addComponent(cb_spoetregler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rbtn_hommeregler)
                        .addComponent(rbtn_femmeregler)
                        .addComponent(alb_sexeregler)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable_abonnementtermine.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PRENOM", "NOM", "DATE DEBUT  ", "DATE FIN"
            }
        ));
        jScrollPane3.setViewportView(jTable_abonnementtermine);

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setForeground(new java.awt.Color(255, 153, 153));

        jLabel51.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 14)); // NOI18N
        jLabel51.setText("Impayés");

        nb_impayés.setFont(new java.awt.Font("Microsoft YaHei UI Light", 1, 48)); // NOI18N
        nb_impayés.setText("124");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(nb_impayés, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(nb_impayés, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel22.setBackground(new java.awt.Color(228, 67, 13));
        jPanel22.setPreferredSize(new java.awt.Dimension(5, 0));

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 118, Short.MAX_VALUE)
        );

        msg_renouvler1.setBackground(new java.awt.Color(255, 255, 204));

        jLabel52.setFont(new java.awt.Font("Microsoft YaHei Light", 0, 14)); // NOI18N
        jLabel52.setText("Cliquer deux foix sur la ligne du member pour renouvler l'abonement");

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(204, 204, 0));
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setText("!");

        javax.swing.GroupLayout msg_renouvler1Layout = new javax.swing.GroupLayout(msg_renouvler1);
        msg_renouvler1.setLayout(msg_renouvler1Layout);
        msg_renouvler1Layout.setHorizontalGroup(
            msg_renouvler1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(msg_renouvler1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        msg_renouvler1Layout.setVerticalGroup(
            msg_renouvler1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, msg_renouvler1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(msg_renouvler1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(msg_renouvler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(121, 121, 121))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1029, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(22, Short.MAX_VALUE))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jpanel_rechregler, javax.swing.GroupLayout.PREFERRED_SIZE, 1029, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpanel_rechregler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 30, Short.MAX_VALUE)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                            .addComponent(msg_renouvler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(27, 27, 27))
                        .addGroup(jPanel24Layout.createSequentialGroup()
                            .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap()))))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, 1050, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Abonnements terminés ", jPanel14);

        jPanel23.setBackground(new java.awt.Color(242, 237, 237));
        jPanel23.setPreferredSize(new java.awt.Dimension(1371, 642));

        jpanel_rech.setBackground(new java.awt.Color(255, 255, 255));
        jpanel_rech.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jpanel_rech.setForeground(new java.awt.Color(255, 51, 0));

        rbtn_nompredatAT.setBackground(new java.awt.Color(255, 255, 255));
        rbtn_nompredatAT.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 12)); // NOI18N
        rbtn_nompredatAT.setText("Nom/prenom/Date Naissance");
        rbtn_nompredatAT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_nompredatATActionPerformed(evt);
            }
        });

        rbtn_IDAT.setBackground(new java.awt.Color(255, 255, 255));
        rbtn_IDAT.setFont(new java.awt.Font("Microsoft JhengHei UI Light", 0, 12)); // NOI18N
        rbtn_IDAT.setText("ID");

        txt_IDAT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_IDATActionPerformed(evt);
            }
        });

        rbt_sportsAT.setBackground(new java.awt.Color(255, 255, 255));
        rbt_sportsAT.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 12)); // NOI18N
        rbt_sportsAT.setText("Sport");

        cb_spoetAT.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 11)); // NOI18N
        cb_spoetAT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "GYM", "KARATE", "PICINE" }));

        rbtn_hommeAT.setBackground(new java.awt.Color(255, 255, 255));
        rbtn_hommeAT.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 12)); // NOI18N
        rbtn_hommeAT.setText("Homme");

        rbtn_femmeAT.setBackground(new java.awt.Color(255, 255, 255));
        rbtn_femmeAT.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 12)); // NOI18N
        rbtn_femmeAT.setText("Femme");

        alb_sexeAT.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 14)); // NOI18N
        alb_sexeAT.setText("Sexe :");

        btn_rechercheAT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_rechercheAT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_proj/magnifying-glass.png"))); // NOI18N
        btn_rechercheAT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_rechercheATMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpanel_rechLayout = new javax.swing.GroupLayout(jpanel_rech);
        jpanel_rech.setLayout(jpanel_rechLayout);
        jpanel_rechLayout.setHorizontalGroup(
            jpanel_rechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel_rechLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtn_nompredatAT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_nompredatAT, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtn_IDAT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_IDAT, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbt_sportsAT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cb_spoetAT, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(alb_sexeAT, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtn_hommeAT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtn_femmeAT, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_rechercheAT)
                .addGap(73, 73, 73))
        );
        jpanel_rechLayout.setVerticalGroup(
            jpanel_rechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel_rechLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanel_rechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_rechercheAT)
                    .addGroup(jpanel_rechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rbtn_nompredatAT)
                        .addComponent(txt_nompredatAT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rbtn_IDAT)
                        .addComponent(txt_IDAT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rbt_sportsAT)
                        .addComponent(cb_spoetAT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rbtn_hommeAT)
                        .addComponent(rbtn_femmeAT)
                        .addComponent(alb_sexeAT)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable_impayé.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DOIt PAYER", " TOTAL PAYE ", " PRENOM", "NOM", "SPORT", "ABONNEMENT", "PRIX", "DATE DEBUT", "DATE FIN ", "recus"
            }
        ));
        jTable_impayé.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_impayéMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable_impayé);
        if (jTable_impayé.getColumnModel().getColumnCount() > 0) {
            jTable_impayé.getColumnModel().getColumn(9).setResizable(false);
        }

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setForeground(new java.awt.Color(255, 153, 153));

        jLabel2.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 14)); // NOI18N
        jLabel2.setText("Les impayés");

        nb_abonnementTR.setFont(new java.awt.Font("Microsoft YaHei UI Light", 1, 48)); // NOI18N
        nb_abonnementTR.setText("9");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(nb_abonnementTR, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(nb_abonnementTR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel18.setBackground(new java.awt.Color(228, 67, 13));
        jPanel18.setPreferredSize(new java.awt.Dimension(5, 0));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 118, Short.MAX_VALUE)
        );

        msg_renouvler.setBackground(new java.awt.Color(255, 255, 204));

        jLabel48.setFont(new java.awt.Font("Microsoft YaHei Light", 0, 14)); // NOI18N
        jLabel48.setText("Cliquer deux foix sur la ligne du member pour régler les paiements");

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(204, 204, 0));
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("!");

        javax.swing.GroupLayout msg_renouvlerLayout = new javax.swing.GroupLayout(msg_renouvler);
        msg_renouvler.setLayout(msg_renouvlerLayout);
        msg_renouvlerLayout.setHorizontalGroup(
            msg_renouvlerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(msg_renouvlerLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        msg_renouvlerLayout.setVerticalGroup(
            msg_renouvlerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, msg_renouvlerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(msg_renouvlerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(msg_renouvler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(121, 121, 121))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1029, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(22, Short.MAX_VALUE))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(jpanel_rech, javax.swing.GroupLayout.PREFERRED_SIZE, 1029, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpanel_rech, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 30, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                            .addComponent(msg_renouvler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(27, 27, 27))
                        .addGroup(jPanel23Layout.createSequentialGroup()
                            .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap()))))
        );

        jTabbedPane1.addTab("les impayés", jPanel23);

        jPanel3.setBackground(new java.awt.Color(242, 237, 237));

        tbl_memberReg.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID members", "Prenom", "Nom "
            }
        ));
        tbl_memberReg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_memberRegMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbl_memberReg);

        tbl_regelementP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID members", "Date paiment ", "Montant payé"
            }
        ));
        jScrollPane6.setViewportView(tbl_regelementP);

        jpanel_rech1.setBackground(new java.awt.Color(255, 255, 255));
        jpanel_rech1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jpanel_rech1.setForeground(new java.awt.Color(255, 51, 0));

        rbtn_nompredatREG.setBackground(new java.awt.Color(255, 255, 255));
        rbtn_nompredatREG.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 12)); // NOI18N
        rbtn_nompredatREG.setText("Nom/prenom/Date Naissance");
        rbtn_nompredatREG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_nompredatREGActionPerformed(evt);
            }
        });

        rbtn_IDREG.setBackground(new java.awt.Color(255, 255, 255));
        rbtn_IDREG.setFont(new java.awt.Font("Microsoft JhengHei UI Light", 0, 12)); // NOI18N
        rbtn_IDREG.setText("ID");

        txt_IDREG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_IDREGActionPerformed(evt);
            }
        });

        rbt_sportsREG.setBackground(new java.awt.Color(255, 255, 255));
        rbt_sportsREG.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 12)); // NOI18N
        rbt_sportsREG.setText("Sport");

        cb_spoetREG.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 11)); // NOI18N
        cb_spoetREG.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "GYM", "KARATE", "PICINE" }));

        rbtn_hommeREG.setBackground(new java.awt.Color(255, 255, 255));
        rbtn_hommeREG.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 12)); // NOI18N
        rbtn_hommeREG.setText("Homme");

        rbtn_femmeREG.setBackground(new java.awt.Color(255, 255, 255));
        rbtn_femmeREG.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 12)); // NOI18N
        rbtn_femmeREG.setText("Femme");

        alb_sexeREG.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 14)); // NOI18N
        alb_sexeREG.setText("Sexe :");

        btn_rechercheREG.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_rechercheREG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_proj/magnifying-glass.png"))); // NOI18N
        btn_rechercheREG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_rechercheREGMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpanel_rech1Layout = new javax.swing.GroupLayout(jpanel_rech1);
        jpanel_rech1.setLayout(jpanel_rech1Layout);
        jpanel_rech1Layout.setHorizontalGroup(
            jpanel_rech1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel_rech1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtn_nompredatREG)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_nompredatREG, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtn_IDREG)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_IDREG, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbt_sportsREG)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cb_spoetREG, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(alb_sexeREG, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtn_hommeREG)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtn_femmeREG, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_rechercheREG)
                .addGap(73, 73, 73))
        );
        jpanel_rech1Layout.setVerticalGroup(
            jpanel_rech1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel_rech1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanel_rech1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_rechercheREG)
                    .addGroup(jpanel_rech1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rbtn_nompredatREG)
                        .addComponent(txt_nompredatREG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rbtn_IDREG)
                        .addComponent(txt_IDREG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rbt_sportsREG)
                        .addComponent(cb_spoetREG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rbtn_hommeREG)
                        .addComponent(rbtn_femmeREG)
                        .addComponent(alb_sexeREG)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel25.setBackground(new java.awt.Color(102, 102, 102));
        jPanel25.setPreferredSize(new java.awt.Dimension(2, 460));

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 460, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jpanel_rech1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1029, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpanel_rech1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(93, 93, 93))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPane1.addTab("Regelements", jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1055, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(228, 67, 13));

        btn_members.setBackground(new java.awt.Color(228, 67, 13));
        btn_members.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_membersMouseClicked(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_proj/community.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Members");

        javax.swing.GroupLayout btn_membersLayout = new javax.swing.GroupLayout(btn_members);
        btn_members.setLayout(btn_membersLayout);
        btn_membersLayout.setHorizontalGroup(
            btn_membersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_membersLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btn_membersLayout.setVerticalGroup(
            btn_membersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_membersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btn_membersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        btn_abonnement.setBackground(new java.awt.Color(228, 67, 13));
        btn_abonnement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_abonnementMouseClicked(evt);
            }
        });

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_proj/date (1).png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Abonnement");

        javax.swing.GroupLayout btn_abonnementLayout = new javax.swing.GroupLayout(btn_abonnement);
        btn_abonnement.setLayout(btn_abonnementLayout);
        btn_abonnementLayout.setHorizontalGroup(
            btn_abonnementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_abonnementLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btn_abonnementLayout.setVerticalGroup(
            btn_abonnementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_abonnementLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btn_abonnementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btn_paiments.setBackground(new java.awt.Color(228, 67, 13));
        btn_paiments.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_paimentsMouseClicked(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_proj/hand (1).png"))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Paiements");

        javax.swing.GroupLayout btn_paimentsLayout = new javax.swing.GroupLayout(btn_paiments);
        btn_paiments.setLayout(btn_paimentsLayout);
        btn_paimentsLayout.setHorizontalGroup(
            btn_paimentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_paimentsLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btn_paimentsLayout.setVerticalGroup(
            btn_paimentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_paimentsLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(btn_paimentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btn_consultation.setBackground(new java.awt.Color(228, 67, 13));
        btn_consultation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_consultationMouseClicked(evt);
            }
        });

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_proj/chat (1).png"))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Consultation");

        btn_statistiques.setBackground(new java.awt.Color(228, 67, 13));
        btn_statistiques.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_statistiquesMouseClicked(evt);
            }
        });

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_proj/bar-chart.png"))); // NOI18N

        jLabel14.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Statistiques");

        btn_statistiques1.setBackground(new java.awt.Color(228, 67, 13));
        btn_statistiques1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_statistiques1MouseClicked(evt);
            }
        });

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_proj/bar-chart.png"))); // NOI18N

        jLabel55.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 14)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("Coach");

        javax.swing.GroupLayout btn_statistiques1Layout = new javax.swing.GroupLayout(btn_statistiques1);
        btn_statistiques1.setLayout(btn_statistiques1Layout);
        btn_statistiques1Layout.setHorizontalGroup(
            btn_statistiques1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_statistiques1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel54)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btn_statistiques1Layout.setVerticalGroup(
            btn_statistiques1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_statistiques1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btn_statistiques1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout btn_statistiquesLayout = new javax.swing.GroupLayout(btn_statistiques);
        btn_statistiques.setLayout(btn_statistiquesLayout);
        btn_statistiquesLayout.setHorizontalGroup(
            btn_statistiquesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_statistiquesLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btn_statistiques1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        btn_statistiquesLayout.setVerticalGroup(
            btn_statistiquesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_statistiquesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btn_statistiquesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(btn_statistiques1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout btn_consultationLayout = new javax.swing.GroupLayout(btn_consultation);
        btn_consultation.setLayout(btn_consultationLayout);
        btn_consultationLayout.setHorizontalGroup(
            btn_consultationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_consultationLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(btn_statistiques, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        btn_consultationLayout.setVerticalGroup(
            btn_consultationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_consultationLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(btn_consultationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(btn_statistiques, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_proj/exercise.png"))); // NOI18N

        jLabel32.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 10)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_proj/logout.png"))); // NOI18N
        jLabel32.setText("Logout");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_members, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_abonnement, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_paiments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_consultation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(btn_members, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btn_abonnement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btn_paiments, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btn_consultation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 226, Short.MAX_VALUE)
                .addComponent(jLabel32)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 1058, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1251, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_membersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_membersMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_btn_membersMouseClicked

    private void btn_abonnementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_abonnementMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_btn_abonnementMouseClicked

    private void btn_paimentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_paimentsMouseClicked
             // TODO add your handling code here:
         jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_btn_paimentsMouseClicked

    private void btn_consultationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_consultationMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_btn_consultationMouseClicked

    private void btn_statistiquesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_statistiquesMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(4);
    }//GEN-LAST:event_btn_statistiquesMouseClicked

    private void btn_rechercheMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_rechercheMouseClicked
        // TODO add your handling code here:
        if (rbtn_nompredat.isSelected()){
            rq_nom=txt_nompredat.getText() ;
        }
        if (rbtn_nompredat.isSelected()){
            rq_prenom=txt_nompredat.getText() ;
        }
        if (rbtn_nompredat.isSelected()){
            rq_dateNaissance=txt_nompredat.getText() ;
        }
        if (rbtn_ID.isSelected()){
            rq_ID=Integer.parseInt(txt_ID.getText());
        }
        if (rbtn_homme.isSelected()){
            rq_sexe="homme" ;
            rbtn_femme.setSelected(false);
        }
        if (rbtn_femme.isSelected()){
            rq_sexe="femme";
            rbtn_homme.setSelected(false);
        }
        req.setText("requte fiha :"+rq_nom+rq_prenom+rq_ID+rq_dateNaissance+rq_sexe+"BRAVO");

    }//GEN-LAST:event_btn_rechercheMouseClicked

    private void txt_IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_IDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_IDActionPerformed

    private void rbtn_nompredatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_nompredatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtn_nompredatActionPerformed

    private void btn_abonnemetencoursKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_abonnemetencoursKeyPressed
                // TODO add your handling code here:
         
    }//GEN-LAST:event_btn_abonnemetencoursKeyPressed

    private void btn_abonnemetencoursMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_abonnemetencoursMouseClicked
         // TODO add your handling code here:
          jTabbedPane1.setSelectedIndex(5); 
    }//GEN-LAST:event_btn_abonnemetencoursMouseClicked

    private void btn_ExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ExitMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btn_ExitMouseClicked

    private void rbtn_nompredatATActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_nompredatATActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtn_nompredatATActionPerformed

    private void txt_IDATActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_IDATActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_IDATActionPerformed

    private void btn_rechercheATMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_rechercheATMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_rechercheATMouseClicked
    private int xMouse ,yMouse ;
    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        // TODO add your handling code here:
        xMouse=evt.getX();
        yMouse=evt.getY();
    }//GEN-LAST:event_jPanel1MousePressed

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
        // TODO add your handling code here:
        int x =evt.getXOnScreen();
        int y =evt.getYOnScreen();
        setLocation(x-xMouse ,y-yMouse);

    }//GEN-LAST:event_jPanel1MouseDragged

    private void btn_abonnementterminesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_abonnementterminesMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(9);
        
    }//GEN-LAST:event_btn_abonnementterminesMouseClicked

    private void btn_lesimpayésMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_lesimpayésMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(8);
        
    }//GEN-LAST:event_btn_lesimpayésMouseClicked

    private void rbtn_nompredatreglerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_nompredatreglerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtn_nompredatreglerActionPerformed

    private void txt_IDreglerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_IDreglerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_IDreglerActionPerformed

    private void btn_recherchereglerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_recherchereglerMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_recherchereglerMouseClicked

    private void btn_statistiques1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_statistiques1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_statistiques1MouseClicked

    private void btn_reglerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_reglerMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(8);
        
    }//GEN-LAST:event_btn_reglerMouseClicked

    private void btn_reglementsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_reglementsMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(6);
    }//GEN-LAST:event_btn_reglementsMouseClicked

    private void btn_historiqueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_historiqueMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(7);
    }//GEN-LAST:event_btn_historiqueMouseClicked

    private void rbtn_nompredatREGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_nompredatREGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtn_nompredatREGActionPerformed

    private void txt_IDREGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_IDREGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_IDREGActionPerformed

    private void btn_rechercheREGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_rechercheREGMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_rechercheREGMouseClicked

    private void jTable_impayéMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_impayéMouseClicked
        int i = jTable_impayé.getSelectedRow();
        TableModel model = jTable_impayé.getModel();
        String nom = (String) model.getValueAt(i, 3);
        String prenom = (String) model.getValueAt(i, 3);
        int recus = (int) model.getValueAt(i,0);
        double montantApaye = (double) model.getValueAt(i,0);
        new reglement(nom,prenom,recus ,montantApaye).setVisible(true);
    }//GEN-LAST:event_jTable_impayéMouseClicked

    private void tbl_memberRegMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_memberRegMouseClicked
        int i = tbl_memberReg.getSelectedRow();
        TableModel model = tbl_memberReg.getModel();
        int id = (int) model.getValueAt(i,0);
        reglementP(id);
    }//GEN-LAST:event_tbl_memberRegMouseClicked
    private String rq_nom  ;
    private String rq_prenom  ;
    private String rq_dateNaissance ;
    private String rq_sexe ;
    private String rq_sport ;
    private int rq_ID ;
    ;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new home().setVisible(true);
            } catch (ParseException ex) {
                Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel alb_sexeAT;
    private javax.swing.JLabel alb_sexeREG;
    private javax.swing.JLabel alb_sexeregler;
    private javax.swing.JLabel btn_Exit;
    private javax.swing.JPanel btn_abonnement;
    private javax.swing.JPanel btn_abonnementtermines;
    private javax.swing.JPanel btn_abonnemetencours;
    private javax.swing.JPanel btn_consultation;
    private javax.swing.JPanel btn_historique;
    private javax.swing.JPanel btn_lesimpayés;
    private javax.swing.JPanel btn_members;
    private javax.swing.JPanel btn_paiments;
    private javax.swing.JLabel btn_recherche;
    private javax.swing.JLabel btn_rechercheAT;
    private javax.swing.JLabel btn_rechercheREG;
    private javax.swing.JLabel btn_rechercheregler;
    private javax.swing.JPanel btn_reglements;
    private javax.swing.JPanel btn_regler;
    private javax.swing.JPanel btn_statistiques;
    private javax.swing.JPanel btn_statistiques1;
    private javax.swing.JComboBox<String> cb_spoetAT;
    private javax.swing.JComboBox<String> cb_spoetREG;
    private javax.swing.JComboBox<String> cb_spoetregler;
    private javax.swing.JComboBox<String> cb_sport;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable_abonnementsencours;
    private javax.swing.JTable jTable_abonnementtermine;
    private javax.swing.JTable jTable_impayé;
    private javax.swing.JPanel jpanel_rech;
    private javax.swing.JPanel jpanel_rech1;
    private javax.swing.JPanel jpanel_rechregler;
    private javax.swing.JPanel msg_renouvler;
    private javax.swing.JPanel msg_renouvler1;
    private javax.swing.JLabel nb_abonnementTR;
    private javax.swing.JLabel nb_impayés;
    private javax.swing.JPanel panelrechercher;
    private javax.swing.JRadioButton rbt_sports;
    private javax.swing.JRadioButton rbt_sportsAT;
    private javax.swing.JRadioButton rbt_sportsREG;
    private javax.swing.JRadioButton rbt_sportsregler;
    private javax.swing.JRadioButton rbtn_ID;
    private javax.swing.JRadioButton rbtn_IDAT;
    private javax.swing.JRadioButton rbtn_IDREG;
    private javax.swing.JRadioButton rbtn_IDregler;
    private javax.swing.JRadioButton rbtn_femme;
    private javax.swing.JRadioButton rbtn_femmeAT;
    private javax.swing.JRadioButton rbtn_femmeREG;
    private javax.swing.JRadioButton rbtn_femmeregler;
    private javax.swing.JRadioButton rbtn_homme;
    private javax.swing.JRadioButton rbtn_hommeAT;
    private javax.swing.JRadioButton rbtn_hommeREG;
    private javax.swing.JRadioButton rbtn_hommeregler;
    private javax.swing.JRadioButton rbtn_nompredat;
    private javax.swing.JRadioButton rbtn_nompredatAT;
    private javax.swing.JRadioButton rbtn_nompredatREG;
    private javax.swing.JRadioButton rbtn_nompredatregler;
    private javax.swing.JLabel req;
    private javax.swing.JTable table_historique;
    private javax.swing.JTable tbl_memberReg;
    private javax.swing.JTable tbl_regelementP;
    private javax.swing.JTextField txt_ID;
    private javax.swing.JTextField txt_IDAT;
    private javax.swing.JTextField txt_IDREG;
    private javax.swing.JTextField txt_IDregler;
    private javax.swing.JTextField txt_nompredat;
    private javax.swing.JTextField txt_nompredatAT;
    private javax.swing.JTextField txt_nompredatREG;
    private javax.swing.JTextField txt_nompredatregler;
    // End of variables declaration//GEN-END:variables

    void setVisible() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private int  prixAbonnement(int typeAbonnement) {
        switch (typeAbonnement) {
            case 1:
                return 100 ;
            case 6:
                return 600 ;
            default:
                return 1200 ;
        }
}
}
