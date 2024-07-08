import java.util.*;

class Product {
    private final String name;
    private final double price;
    private final int inventory;
    private final String seller;
    private final String preview;

    public Product(String name, double price, int inventory, String seller) {
        this.name = name;
        this.price = price;
        this.inventory = inventory;
        this.seller = seller;
        this.preview = "";
    }

    public  String getProductName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public int getInventory() {
        return inventory;
    }
    public String getSeller() {
        return seller;
    }
    public String getPreview() {
        return preview;
    }
}

class Order {
    private final int orderId;
    private final Account user;
    private final List<Product> products;
    private double totalPrice;

    public Order(int orderId, Account user, List<Product> products) {
        this.orderId = orderId;
        this.user = user;
        this.products = products;
        this.totalPrice = 0.0;
    }
    public int getOrderId() {
        return orderId;
    }
    public Account getUser() {
        return user;
    }
    public List<Product> getProducts() {
        return products;
    }
    public double getTotalPrice() {
        for (Product product : products) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }
}

class Wallet {
    private double inventory;

    public Wallet() {
        this.inventory = 0.0;
    }
    public double getInventory() {
        return inventory;
    }
    public void increaseInventory(double amount) {
        inventory += amount;
    }
    public void decreaseInventory(double amount) {
        inventory -= amount;
    }
}

class Recommend {
    private final List<Order> orders;
    private final List<Wallet> wallets;
    Recommend() {
        this.orders = new ArrayList<>();
        this.wallets = new ArrayList<>();
    }
    public void addOrder(Order order) {
        orders.add(order);
    }
    public void addWallet(Wallet wallet) {
        wallets.add(wallet);
    }
    public  Wallet getWallet(int index) {
        return wallets.get(index);
    }
    public  Order getOrder(int index) {
        return orders.get(index);
    }
}

abstract class Account {
    private String UserName;
    private String UserPass;
    public enum UserPermission { admin, seller, customer }
    private UserPermission Permission;
    private String UserMail;
    private String UserPhone;
    private String UserAddress;
    private final List<Order> OrderList;
    private final Wallet UserWallet;
    private String CompanyName;
    private final List<Recommend> recommends;
    public Account(String userName, String userPass, UserPermission userPermission, String userMail) {
        this.UserName = userName;
        this.UserPass = userPass;
        this.Permission = userPermission;
        this.UserMail = userMail;
        recommends = new ArrayList<>();
        OrderList = new ArrayList<>();
        UserWallet = getWallet();
    }
    public static void logIn(Shop shop) {
        int indexOfUser;
        String userName, userPassword;
        Scanner input = new Scanner(System.in);
        ArrayList<String> AccountNameList = new ArrayList<>();
        for (Account user : shop.getUsers()) AccountNameList.add(user.getUserName());
        while (true) {
            System.out.println("Please enter username:(Enter 0 to back)");
            userName = input.nextLine();
            if(userName.equals("0"))
                return;

            System.out.println("Please enter password:");
            userPassword = input.nextLine();

            indexOfUser = AccountNameList.indexOf(userName);
            if(indexOfUser != -1) {
                if (shop.getUsers().get(indexOfUser).getUserPass().equals(userPassword)) {
                    switch (shop.getUsers().get(indexOfUser).getUserPermission()) {
                        case admin:
                            shop.adminMenu();
                            break;
                        case seller:
                            shop.sellerMenu(shop.getUsers().get(indexOfUser));
                            break;
                        case customer:
                            shop.customerMenu(shop.getUsers().get(indexOfUser));
                            break;
                    }
                } else
                    System.out.println("password is not correct! :)");
            } else
                System.out.println("username is not exist! :)");
        }
    }
    public static void signIn(Shop shop) {
        int indexOfUser;
        String userName, userPassword, accountType, userEmail, userPhone, UserAddress;
        Scanner input = new Scanner(System.in);
        ArrayList<String> AccountNameList = new ArrayList<>();
        for (Account user : shop.getUsers()) AccountNameList.add(user.getUserName());
        while (true) {
            System.out.println("Please enter username:(Enter 0 to back)");
            userName = input.nextLine();
            if(userName.equals("0")  || userName.isEmpty())
                return;

            System.out.println("Please enter password:(Enter 0 to back)");
            userPassword = input.nextLine();
            if(userPassword.equals("0")  || userPassword.isEmpty())
                return;

            indexOfUser = AccountNameList.indexOf(userName);//??
            if(indexOfUser == -1) {
                System.out.println("1. customer or 2. seller?(Enter 1 or 2 or enter 0 to back)");
                accountType = input.nextLine();
                if(accountType.equals("0") || accountType.isEmpty())
                    return;
                switch(accountType) {
                    case "1":
                        System.out.println("Please Enter Email address?(Enter 0 to back)");
                        userEmail = input.nextLine();
                        if(userEmail.equals("0") || userEmail.isEmpty())
                            return;
                        System.out.println("Please Enter phone number?(Enter 0 to back)");
                        userPhone = input.nextLine();
                        if(userPhone.equals("0") || userPhone.isEmpty())
                            return;
                        System.out.println("Please Enter address?(Enter 0 to back)");
                        UserAddress = input.nextLine();
                        if(UserAddress.equals("0") || UserAddress.isEmpty())
                            return;
                        Customer customer = new Customer(userName, userPassword, UserPermission.customer,
                                userEmail, userPhone, UserAddress);
                        shop.addUser(customer);
                        System.out.println("your Account successfully created");
                        return;
                    case "2":
                        String companyName;
                        System.out.println("Please Enter Email address?(Enter 0 to back)");
                        userEmail = input.nextLine();
                        if(userEmail.equals("0") || userEmail.isEmpty())
                            return;
                        System.out.println("Please Enter phone number?(Enter 0 to back)");
                        userPhone = input.nextLine();
                        if(userPhone.equals("0") || userPhone.isEmpty())
                            return;
                        System.out.println("Please Enter address?(Enter 0 to back)");
                        UserAddress = input.nextLine();
                        if(UserAddress.equals("0") || UserAddress.isEmpty())
                            return;
                        System.out.println("Please Enter company name?(Enter 0 to back)");
                        companyName = input.nextLine();
                        if(companyName.equals("0") || companyName.isEmpty())
                            return;
                        Seller seller = new Seller(userName, userPassword, UserPermission.seller,
                                userEmail, userPhone, UserAddress, companyName);
                        shop.addUser(seller);
                        System.out.println("Account has built.");
                        return;
                }
            } else
                System.out.println("This username has already been used! :)");
        }
    }
    public String getUserName() {
        return UserName;
    }
    public String getUserPass() {
        return UserPass;
    }
    public UserPermission getUserPermission() {
        return Permission;
    }
    public String getUserMail() {
        return UserMail;
    }
    public String getUserPhone() {
        return UserPhone;
    }
    public String getUserAddress() {
        return UserAddress;
    }
    public List<Order> getOrderList() {
        return OrderList;
    }
    public Wallet getWallet() {
        return UserWallet;
    }
    public void setUserName(String userName) {
        UserName = userName;
    }
    public void setUserPass(String userPass) {
        UserPass = userPass;
    }
    public void setUserPermission(UserPermission userPermission) {
        Permission = userPermission;
    }
    public void setUserMail(String userMail) {
        UserMail = userMail;
    }
    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }
    public void setUserAddress(String userAddress) {
        UserAddress = userAddress;
    }
    public String getCompanyName() {
        return CompanyName;
    }
    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }
    public void addRecommend(Recommend recommend) {
        recommends.add(recommend);
    }
    public List<Recommend> getRecommends() {
        return recommends;
    }
    public void deleteRecommend(int index) {
        recommends.remove(index);
    }
}

class Admin extends Account {
    public Admin(String username, String password, UserPermission permission, String email) {
        super(username, password, permission, email);
    }
}

class Seller extends Account {
    public Seller(String username, String password, UserPermission permission, String email, String phone, String address, String companyName) {
        super(username, password, permission, email);
        setUserPhone(phone);
        setUserAddress(address);
        setCompanyName(companyName);
    }
}

class Customer extends Account {
    public Customer(String username, String password, UserPermission permission, String email, String phone, String address) {
        super(username, password, permission, email);
        setUserPhone(phone);
        setUserAddress(address);
    }
}

class Shop {
    private String ShopName;
    private String WebAddress;
    private String SupportNumber;
    private final List<Account> Users;
    private final List<Product> Products;
    private final List<Order> Orders;
    private final Wallet shopWallet;

    public Shop(String shopName, String webAddress, String supportNumber) {
        setShopName(shopName);
        setWebAddress(webAddress);
        setSupportNumber(supportNumber);
        this.Users = new ArrayList<>();
        this.Products = new ArrayList<>();
        this.Orders = new ArrayList<>();
        this.shopWallet = new Wallet();
    }
    public void adminMenu() {
        Scanner input = new Scanner(System.in);
        int selectedMenu, accountId;
        String userName, userPassword, accountType, userEmail, userPhone, userAddress, companyName;
        while(true) {
            System.out.println("admin menu:");
            System.out.println("1. Users List\n2. Products List\n3. Orders List\n4. Total Profit\n5. recommends List\n0. logOut");
            selectedMenu = input.nextInt();
            if(selectedMenu == 0)
                return;
            switch (selectedMenu) {
                case 1:
                    while(true) {
                        for (Account value : Users) {
                            System.out.println(Users.indexOf(value)+ " " + value.getUserName() + "\n\n");
                        }
                        System.out.println("User list menu:(Enter 0 to back)");
                        System.out.println("1. Add User\n2. Delete user\n3. Show user information\n4. Change user information");
                        selectedMenu = input.nextInt();
                        if(selectedMenu == 0)
                            break;
                        switch(selectedMenu) {
                            case 1:
                                Account.signIn(this);
                                break;
                            case 2:
                                System.out.println("Enter Account Id:(Enter 0 to back)");
                                accountId = input.nextInt();
                                deleteUser(accountId);
                                break;
                            case 3:
                                System.out.println("Enter Account Id:(Enter 0 to back)");
                                accountId = input.nextInt();
                                if(this.Users.get(accountId).getUserPermission().equals(Account.UserPermission.customer)) {
                                    System.out.println("ID\tUsername\tPassword\tPermission\tEmail\tPhone\tAddress\tWallet\n");
                                    System.out.println(accountId + "\t" + this.Users.get(accountId).getUserName()
                                            + "\t" + this.Users.get(accountId).getUserPass() + "\t" + this.Users.get(accountId).getUserPermission()
                                            + "\t" + this.Users.get(accountId).getUserMail() + "\t" + this.Users.get(accountId).getUserPhone()
                                            + "\t" + this.Users.get(accountId).getUserAddress() + "\t" + this.Users.get(accountId).getWallet());
                                } else if(this.Users.get(accountId).getUserPermission().equals(Account.UserPermission.seller)) {
                                System.out.println("ID\tUsername\tPassword\tPermission\tEmail\tPhone\tAddress\tWallet\tCompanyName\n");
                                System.out.println(accountId + "\t" + this.Users.get(accountId).getUserName()
                                        + "\t" + this.Users.get(accountId).getUserPass() + "\t" + this.Users.get(accountId).getUserPermission()
                                        + "\t" + this.Users.get(accountId).getUserMail() + "\t" + this.Users.get(accountId).getUserPhone()
                                        + "\t" + this.Users.get(accountId).getUserAddress() + "\t" + this.Users.get(accountId).getWallet()
                                        + "\t" + this.Users.get(accountId).getCompanyName());
                                }
                                break;
                            case 4:
                                int changeIndex;
                                System.out.println("Enter Account Id:(Enter 0 to back)");
                                accountId = input.nextInt();
                                if(this.Users.get(accountId).getUserPermission().equals(Account.UserPermission.customer)) {
                                    System.out.println("Enter witch one to change?\n1. Username\n2.Password\n3. Permission\n4. Email\n5. Phone\n6. Address");
                                    changeIndex = input.nextInt();
                                    switch(changeIndex) {
                                        case 1:
                                            System.out.println("Please enter new username:(Enter 0 to back)");
                                            userName = input.nextLine();
                                            if(userName.equals("0") || userName.isEmpty())
                                                break;
                                            this.Users.get(accountId).setUserName(userName);
                                            System.out.println("Username is changed to " + this.Users.get(accountId).getUserName() + ".");
                                            break;
                                        case 2:
                                            System.out.println("Please enter new password:(Enter 0 to back)");
                                            userPassword = input.nextLine();
                                            if(userPassword.equals("0") || userPassword.isEmpty())
                                                break;
                                            this.Users.get(accountId).setUserPass(userPassword);
                                            System.out.println("User password is changed to " + this.Users.get(accountId).getUserPass() + ".");
                                            break;
                                        case 3:
                                            System.out.println("Please select account type:(Enter 0 to back)\n1. admin\n2. seller");
                                            accountType = input.nextLine();
                                            if(accountType.equals("0") || accountType.isEmpty())
                                                break;
                                            switch(accountType) {
                                                case "1":
                                                    this.Users.get(accountId).setUserPermission(Account.UserPermission.admin);
                                                    break;
                                                case "2":
                                                    this.Users.get(accountId).setUserPermission(Account.UserPermission.seller);
                                                    break;
                                            }
                                            System.out.println("Account type is changed to " + this.Users.get(accountId).getUserPermission() + ".");
                                            break;
                                        case 4:
                                            System.out.println("Please enter new Email:(Enter 0 to back)");
                                            userEmail = input.nextLine();
                                            if(userEmail.equals("0") || userEmail.isEmpty())
                                                break;
                                            this.Users.get(accountId).setUserMail(userEmail);
                                            System.out.println("User Email is changed to " + this.Users.get(accountId).getUserMail() + ".");
                                            break;
                                        case 5:
                                            System.out.println("Please enter new phone number:(Enter 0 to back)");
                                            userPhone = input.nextLine();
                                            if(userPhone.equals("0") || userPhone.isEmpty())
                                                break;
                                            this.Users.get(accountId).setUserPhone(userPhone);
                                            System.out.println("User phone is changed to " + this.Users.get(accountId).getUserPhone() + ".");
                                            break;
                                        case 6:
                                            System.out.println("Please enter new address:(Enter 0 to back)");
                                            userAddress = input.nextLine();
                                            if(userAddress.equals("0") || userAddress.isEmpty())
                                                break;
                                            this.Users.get(accountId).setUserAddress(userAddress);
                                            System.out.println("User address is changed to " + this.Users.get(accountId).getUserAddress() + ".");
                                            break;
                                    }
                                } else if(this.Users.get(accountId).getUserPermission().equals(Account.UserPermission.seller)) {
                                    System.out.println("Enter witch one to change?\n1. Username\n2.Password\n3. Permission\n4. Email\n5. Phone\n6. Address\n7. CompanyName");
                                    changeIndex = input.nextInt();
                                    switch(changeIndex) {
                                        case 1:
                                            System.out.println("Please enter new username:(Enter 0 to back)");
                                            userName = input.nextLine();
                                            if(userName.equals("0") || userName.isEmpty())
                                                break;
                                            this.Users.get(accountId).setUserName(userName);
                                            System.out.println("Username is changed to " + this.Users.get(accountId).getUserName() + ".");
                                            break;
                                        case 2:
                                            System.out.println("Please enter new password:(Enter 0 to back)");
                                            userPassword = input.nextLine();
                                            if(userPassword.equals("0") || userPassword.isEmpty())
                                                break;
                                            this.Users.get(accountId).setUserPass(userPassword);
                                            System.out.println("User password is changed to " + this.Users.get(accountId).getUserPass() + ".");
                                            break;
                                        case 3:
                                            System.out.println("Please select account type:(Enter 0 to back)\n1. admin\n2. customer");
                                            accountType = input.nextLine();
                                            if(accountType.equals("0") || accountType.isEmpty())
                                                break;
                                            switch(accountType) {
                                                case "1":
                                                    this.Users.get(accountId).setUserPermission(Account.UserPermission.admin);
                                                    break;
                                                case "2":
                                                    this.Users.get(accountId).setUserPermission(Account.UserPermission.customer);
                                                    break;
                                            }
                                            System.out.println("Account type is changed to " + this.Users.get(accountId).getUserPermission() + ".");
                                            break;
                                        case 4:
                                            System.out.println("Please enter new Email:(Enter 0 to back)");
                                            userEmail = input.nextLine();
                                            if(userEmail.equals("0") || userEmail.isEmpty())
                                                break;
                                            this.Users.get(accountId).setUserMail(userEmail);
                                            System.out.println("User Email is changed to " + this.Users.get(accountId).getUserMail() + ".");
                                            break;
                                        case 5:
                                            System.out.println("Please enter new phone number:(Enter 0 to back)");
                                            userPhone = input.nextLine();
                                            if(userPhone.equals("0") || userPhone.isEmpty())
                                                break;
                                            this.Users.get(accountId).setUserPhone(userPhone);
                                            System.out.println("User phone is changed to " + this.Users.get(accountId).getUserPhone() + ".");
                                            break;
                                        case 6:
                                            System.out.println("Please enter new address:(Enter 0 to back)");
                                            userAddress = input.nextLine();
                                            if(userAddress.equals("0") || userAddress.isEmpty())
                                                break;
                                            this.Users.get(accountId).setUserAddress(userAddress);
                                            System.out.println("User address is changed to " + this.Users.get(accountId).getUserAddress() + ".");
                                            break;
                                        case 7:
                                            System.out.println("Please enter new company name:(Enter 0 to back)");
                                            companyName = input.nextLine();
                                            if(companyName.equals("0") || companyName.isEmpty())
                                                break;
                                            this.Users.get(accountId).setCompanyName(companyName);
                                            System.out.println("Company name is changed to " + this.Users.get(accountId).getCompanyName() + ".");
                                            break;
                                    }
                                }
                                break;
                        }
                    }
                    break;
                case 2:
                    for (Product value : Products) {
                        System.out.println(Products.indexOf(value)+ " " + value.getPrice());
                    }
                    break;
                case 3:
                    for (Order value : Orders) {
                        System.out.println(Orders.indexOf(value)+ " ");
                    }
                    break;
                case 4:
                    System.out.println(shopWallet);
                    break;
                case 5:
                    for(int i = 0; i < getUsers().getFirst().getRecommends().size(); i++) {
                        System.out.println(getUsers().getFirst().getRecommends().indexOf(getUsers().getFirst().getRecommends().get(i))
                                + " " + getUsers().getFirst().getRecommends().get(i));
                    }
                    System.out.println("Choose recommend:(Enter -1 to back)");
                    int id = input.nextInt();
                    if(id == -1)
                        return;
                    else {
                        System.out.println(getUsers().getFirst().getRecommends().indexOf(getUsers().getFirst().getRecommends().get(id))
                                + " " + getUsers().getFirst().getRecommends().get(id));
                        System.out.println("1. Accept\n2.Cancel\3. Back");
                        int answer = input.nextInt();
                        if (answer == 3)
                            break;
                        else if (answer == 1) {
                            double amount = getUsers().getFirst().getRecommends().get(id).getOrder(id).getTotalPrice();
                            double sellerAmount = amount*90/100;
                            double shopAmount = amount*10/100;
                            Account sellerAccount = getUsers().getFirst();
                            String seller = getUsers().getFirst().getRecommends().get(id).getOrder(id).getProducts().getFirst().getSeller();
                            for(int i=0; i < getUsers().size(); i++) {
                                if(getUsers().get(i).getUserName().equals(seller)) {
                                    sellerAccount = getUsers().get(i);
                                }
                            }
                            sellerAccount.getWallet().increaseInventory(sellerAmount);
                            shopWallet.increaseInventory(shopAmount);
                            Account customer = getUsers().getFirst().getRecommends().get(id).getOrder(id).getUser();
                            customer.getWallet().decreaseInventory(amount);
                            getUsers().getFirst().deleteRecommend(id);
                        } else if(answer == 2) {
                            getUsers().getFirst().deleteRecommend(id);
                        }
                    }
                    break;
            }
        }
    }
    public void sellerMenu(Account user) {
        Scanner input = new Scanner(System.in);
        int selectedMenu, productId, productInventory;
        String userName, userPassword, userEmail, userPhone, userAddress, companyName, productName;
        double productPrice;
        while(true) {
            System.out.println("seller menu:");
            System.out.println("1. Products List\n2. Account view\n3. recommends List\n0. logOut");
            selectedMenu = input.nextInt();
            if (selectedMenu == 0)
                return;
            switch (selectedMenu) {
                case 1:
                    while(true) {
                        for (Product value : Products) {
                            if(value.getSeller().equals(user.getUserName()))
                                System.out.println(Products.indexOf(value)+ " " + value.getProductName() + " "
                                        + value.getPrice() + " " + value.getInventory() + "\n");
                        }
                        System.out.println("\nProducts list menu:(Enter 0 to back)");
                        System.out.println("1. Add Product\n2. Delete Product\n3. Change Product information");
                        selectedMenu = input.nextInt();
                        if(selectedMenu == 0)
                            break;
                        switch(selectedMenu) {
                            case 1:
                                System.out.println("Enter product name:(Enter 0 to back)");
                                productName = input.nextLine();
                                if(productName.equals("0"))
                                    break;
                                System.out.println("Enter product price:(Enter 0 to back)");
                                productPrice = input.nextDouble();
                                if(productPrice == 0)
                                    break;
                                System.out.println("Enter product inventory:(Enter 0 to back)");
                                productInventory = input.nextInt();
                                if(productInventory == 0)
                                    break;
                                Product product = new Product(productName, productPrice, productInventory, user.getUserName());
                                addProduct(product);
                                System.out.println("The product added.");
                                break;
                            case 2:
                                System.out.println("Enter product Id:(Enter -1 to back)");
                                productId = input.nextInt();
                                if(Products.get(productId).getSeller().equals(user.getUserName()))
                                    deleteProduct(productId);
                                break;
                            case 3:
                                //It's not necessary
                                System.out.println("You can't do it.");
                                break;
                        }
                    }
                    break;
                case 2:
                    int changeIndex;
                    while(true) {
                        System.out.println(user.getUserName() + " " + user.getUserPass() + " " + user.getUserMail()
                                + user.getUserPhone() + " " + user.getUserAddress() + "\n\n");
                        System.out.println("Account list menu:(Enter 0 to back)");
                        System.out.println("1.Change Account information\n");
                        selectedMenu = input.nextInt();
                        if(selectedMenu == 0)
                            break;
                        else if(selectedMenu == 1) {
                            System.out.println("Enter witch one to change?\n1. Username\n2.Password\n3. Email\n4. Phone\n5. Address\n6. Company Name");
                            changeIndex = input.nextInt();
                            switch(changeIndex) {
                                case 1:
                                    System.out.println("Please enter new username:(Enter 0 to back)");
                                    userName = input.nextLine();
                                    if(userName.equals("0") || userName.isEmpty())
                                        break;
                                    user.setUserName(userName);
                                    System.out.println("Username is changed to " + user.getUserName() + ".");
                                    break;
                                case 2:
                                    System.out.println("Please enter new password:(Enter 0 to back)");
                                    userPassword = input.nextLine();
                                    if(userPassword.equals("0") || userPassword.isEmpty())
                                        break;
                                    user.setUserPass(userPassword);
                                    System.out.println("User password is changed to " + user.getUserPass() + ".");
                                    break;
                                case 3:
                                    System.out.println("Please enter new Email:(Enter 0 to back)");
                                    userEmail = input.nextLine();
                                    if(userEmail.equals("0") || userEmail.isEmpty())
                                        break;
                                    user.setUserMail(userEmail);
                                    System.out.println("User Email is changed to " + user.getUserMail() + ".");
                                    break;
                                case 4:
                                    System.out.println("Please enter new phone number:(Enter 0 to back)");
                                    userPhone = input.nextLine();
                                    if(userPhone.equals("0") || userPhone.isEmpty())
                                        break;
                                    user.setUserPhone(userPhone);
                                    System.out.println("User phone is changed to " + user.getUserPhone() + ".");
                                    break;
                                case 5:
                                    System.out.println("Please enter new address:(Enter 0 to back)");
                                    userAddress = input.nextLine();
                                    if(userAddress.equals("0") || userAddress.isEmpty())
                                        break;
                                    user.setUserAddress(userAddress);
                                    System.out.println("User address is changed to " + user.getUserAddress() + ".");
                                    break;
                                case 6:
                                    System.out.println("Please enter new company name:(Enter 0 to back)");
                                    companyName = input.nextLine();
                                    if(companyName.equals("0") || companyName.isEmpty())
                                        break;
                                    user.setCompanyName(companyName);
                                    System.out.println("User address is changed to " + user.getCompanyName() + ".");
                                    break;
                            }
                        }
                    }
                    break;
                case 3:
                    break;
            }
        }
    }
    public void customerMenu(Account user) {
        Scanner input = new Scanner(System.in);
        List<Product> products = new ArrayList<>();
        int productId, orderId;
        String userName, userPassword, userEmail, userPhone, userAddress, productName;
        while(true) {
            System.out.println("customer menu:");
            System.out.println("1. Products List\n2. Buy Basket\n3. recommends List\n4. Account view\n0. logOut");
            switch(input.nextInt()) {
                case 0:
                    return;
                case 1:
                    while(true) {
                        for (Product value : Products) {
                            System.out.println(Products.indexOf(value) + " " + value.getProductName() + " "
                                    + value.getPrice() + " " + value.getInventory() + " " + value.getPreview() + "\n");
                        }
                        System.out.println("Choose your product:(search name of your product or -1 to back)");
                        productName = input.nextLine();
                        if (productName.equals("-1") || productName.isEmpty())
                            return;
                        for (Product value : Products) {
                            if(value.getProductName().equals(productName))
                                System.out.println(Products.indexOf(value) + " " + value.getProductName() + " "
                                    + value.getPrice() + " " + value.getInventory() + "\n");
                        }
                        System.out.println("Enter your product ID:(Enter -1 to back)");
                        productId = input.nextInt();
                        if (productId == -1) {
                            Random rand = new Random();
                            Order order = new Order(rand.nextInt(3), user, products);
                            addOrder(order);
                            return;
                        }
                        System.out.println("Do you want to buy it?\n1.Yes\n2.No");
                        productId = input.nextInt();
                        if(productId == 1) {
                            products.add(this.getProduct(productId));
                        } else if(productId == 2)
                            break;
                    }
                    break;
                case 2:
                    while(true) {
                        for (Order value : Orders) {
                            if(value.getUser().getUserName().equals(user.getUserName()))
                                System.out.println(Orders.indexOf(value) + " " + value.getOrderId() + " " + value.getTotalPrice() + "\n");
                        }
                        System.out.println("Enter Order ID:(Enter -1 to back)");
                        orderId = input.nextInt();
                        if(orderId == -1)
                            return;
                        for (Order value : Orders) {
                            if(value.getUser().getUserName().equals(user.getUserName())) {
                                System.out.println(value.getOrderId() + "\n");
                                for(int i=0; i<value.getProducts().size(); i++) {
                                    System.out.println(value.getProducts().get(i).getProductName() + "\t" + value.getProducts().get(i).getPrice() + "\n");
                                }
                            }
                        }
                        System.out.println("1. Accept\n2.Cancel\3. Back");
                        int answer = input.nextInt();
                        if(answer == 3)
                            break;
                        else if(answer == 1) {
                            Recommend recommend = new Recommend();
                            recommend.addOrder(user.getOrderList().get(orderId));
                            Account admin = getUsers().getFirst();
                            admin.addRecommend(recommend);
                            user.addRecommend(recommend);
                            deleteOrder(orderId);
                        } else if(answer == 2)
                            deleteOrder(orderId);
                    }
                    break;
                case 3:
                    for(int i=0; i < user.getRecommends().size(); i++) {
                        System.out.println(user.getRecommends().get(i));
                    }
                    break;
                case 4:
                    while(true) {
                        System.out.println("Username\tPassword\tEmail\tPhone\tAddress\tWallet Inventory");
                        System.out.println(user.getUserName() + "\t" + user.getUserPass() + "\t" + user.getUserMail()
                                + "\t" + user.getUserPhone() + "\t" + user.getUserAddress() + "\t" + user.getWallet().getInventory() + "\n\n");
                        System.out.println("Account menu:(Enter 0 to back)");
                        System.out.println("1.Change Account information\n");
                        if(input.nextInt() == 1) {
                            System.out.println("""
                                    Enter witch one to change?
                                    1. Username
                                    2.Password
                                    3. Email
                                    4. Phone
                                    5. Address
                                    6. Increase Inventory Recommend""");
                            switch(input.nextInt()) {
                                case 1:
                                    System.out.println("Please enter new username:(Enter 0 to back)");
                                    userName = input.nextLine();
                                    if(userName.equals("0") || userName.isEmpty())
                                        break;
                                    user.setUserName(userName);
                                    System.out.println("Username is changed to " + user.getUserName() + ".");
                                    break;
                                case 2:
                                    System.out.println("Please enter new password:(Enter 0 to back)");
                                    userPassword = input.nextLine();
                                    if(userPassword.equals("0") || userPassword.isEmpty())
                                        break;
                                    user.setUserPass(userPassword);
                                    System.out.println("User password is changed to " + user.getUserPass() + ".");
                                    break;
                                case 3:
                                    System.out.println("Please enter new Email:(Enter 0 to back)");
                                    userEmail = input.nextLine();
                                    if(userEmail.equals("0") || userEmail.isEmpty())
                                        break;
                                    user.setUserMail(userEmail);
                                    System.out.println("User Email is changed to " + user.getUserMail() + ".");
                                    break;
                                case 4:
                                    System.out.println("Please enter new phone number:(Enter 0 to back)");
                                    userPhone = input.nextLine();
                                    if(userPhone.equals("0") || userPhone.isEmpty())
                                        break;
                                    user.setUserPhone(userPhone);
                                    System.out.println("User phone is changed to " + user.getUserPhone() + ".");
                                    break;
                                case 5:
                                    System.out.println("Please enter new address:(Enter 0 to back)");
                                    userAddress = input.nextLine();
                                    if(userAddress.equals("0") || userAddress.isEmpty())
                                        break;
                                    user.setUserAddress(userAddress);
                                    System.out.println("User address is changed to " + user.getUserAddress() + ".");
                                    break;
                                case 6:
                                    System.out.println("Enter the amount:(Enter 0 to back)");
                                    Wallet wallet = new Wallet();
                                    wallet.increaseInventory(input.nextDouble());
                                    Recommend recommend = new Recommend();
                                    recommend.addWallet(wallet);
                                    Account admin = getUsers().getFirst();
                                    admin.addRecommend(recommend);
                                    user.addRecommend(recommend);
                                    break;
                            }
                        } else
                            break;
                    }
                    break;
            }
        }
    }
    public String getShopName() {
        return ShopName;
    }
    public String getWebAddress() {
        return WebAddress;
    }
    public String getSupportNumber() {
        return SupportNumber;
    }
    public List<Account> getUsers() {
        return Users;
    }
    public Product getProduct(int index) {
        return Products.get(index);
    }
    public void setShopName(String shopName) {
        ShopName = shopName;
    }
    public void setWebAddress(String webAddress) {
        WebAddress = webAddress;
    }
    public void setSupportNumber(String supportNumber) {
        SupportNumber = supportNumber;
    }
    public void addUser(Account user) {
        Users.add(user);
    }
    public void addProduct(Product product) {
        Products.add(product);
    }
    public  void addOrder(Order order) {
        Orders.add(order);
    }
    public void deleteUser(int index) {
        Users.remove(index);
    }
    public void deleteProduct(int index) {
        Products.remove(index);
    }
    public void deleteOrder(int index) {
        Orders.remove(index);
    }
}

class OnlineStore {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Shop shop = new Shop("Ehsan shop","https://ehsan-shop.ir","021-45623478");
        Admin admin = new Admin("admin","admin",Account.UserPermission.admin,"admin@gmail.com");
        shop.addUser(admin);
        while(true) {
            System.out.println("\t**************************\nWelcome to " + shop.getShopName() + "\nweb address: " +
                    shop.getWebAddress() + "\nSupport phone: " + shop.getSupportNumber() + "\n\t**************************");
            System.out.println("Select from menu:\n1. login\n2. sign in\n0. exit");
            switch (input.nextInt()) {
                case 0:
                    return;
                case 1:
                    Account.logIn(shop);
                    break;
                case 2:
                    Account.signIn(shop);
                    break;
                default:
                    break;
            }
        }
    }
}