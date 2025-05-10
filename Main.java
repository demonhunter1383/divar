package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    static Repository repository = new Repository();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("hello welcome to divar");
        int back = 0;
        String username = "", password = "";
        while (back == 0) {
            System.out.println("enter 1 to sign in 2 sign up as seller 3 sign up as buyer 4 guest");
            int firstPage = input.nextInt();
            if (firstPage == 1) {
                System.out.println("enter username then password or -1 to back");
                username = input.next();
                if (username.equals("-1")) {
                    break;
                }
                password = input.next();
                String finalUsername1 = username;
                String finalPassword = password;
                repository.getUserList().stream()
                        .filter(user -> user.getUsername().equals(finalUsername1) && user.getPassword().equals(finalPassword))
                        .findFirst()
                        .ifPresentOrElse(user -> {
                            if (user instanceof MainAdmin) {
                                System.out.println("welcome to main admin panel");
                                MainAdmin admin = (MainAdmin) user;
                                showMainAdminPanel(input, admin, repository.getSellerList(), repository.getBuyerList(), repository.getAds(), repository.getNewAds());
                            } else if (user instanceof Buyer) {
                                System.out.println("welcome to buyer panel");
                                Buyer buyer = (Buyer) user;
                                showBuyerPanel(input, buyer, repository.getBuyerList(), repository.getAds());
                            } else if (user instanceof Seller) {
                                System.out.println("welcome to seller panel");
                                Seller seller = (Seller) user;
                                showSellerPanel(input, seller, repository.getSellerList(), repository.getNewAds());
                            } else if (user instanceof Admin) {
                                System.out.println("welcome to admin panel");
                                showAdminPanel(input, repository.getSellerList(), repository.getBuyerList(), repository.getAds(), repository.getNewAds());
                            }
                        }, () -> System.out.println("Invalid username or password"));
            } else if (firstPage == 2) {
                boolean check = false;
                System.out.println("enter new username or -1 to back");
                username = input.next();
                if (username.equals("-1")) {
                    break;
                }
                while (!check) {
                    final String finalUsername = username;
                    if (repository.getSellerList().stream().anyMatch(seller -> seller.getUsername().equals(finalUsername))) {
                        System.out.println("username has been taken enter new username");
                        username = input.next();
                    } else {
                        check = true;
                    }
                }
                if (!username.equals("-1")) {
                    System.out.println("enter password phone number email then width height");
                    Seller seller = new Seller();
                    seller.setUsername(username);
                    seller.setPassword(input.next());
                    seller.setPhoneNumber(input.next());
                    seller.setEmail(input.next());
                    seller.setWidth(input.nextInt());
                    seller.setHeight(input.nextInt());
                    repository.getSellerList().add(seller);
                    System.out.println("welcome to seller panel");
                    showSellerPanel(input, seller, repository.getSellerList(), repository.getNewAds());
                }
            } else if (firstPage == 3) {
                boolean check = false;
                System.out.println("enter new username or -1 to back");
                username = input.next();
                if (username.equals("-1")) {
                    break;
                }
                while (!check) {
                    final String finalUsername = username;
                    if (repository.getBuyerList().stream().anyMatch(buyer -> buyer.getUsername().equals(finalUsername))) {
                        System.out.println("username has been taken enter new username");
                        username = input.next();
                    } else {
                        check = true;
                    }
                }
                if (!username.equals("-1")) {
                    System.out.println("enter password phone number email then width height");
                    Buyer buyer = new Buyer();
                    buyer.setUsername(username);
                    buyer.setPassword(input.next());
                    buyer.setPhoneNumber(input.next());
                    buyer.setEmail(input.next());
                    buyer.setWidth(input.nextInt());
                    buyer.setHeight(input.nextInt());
                    repository.getBuyerList().add(buyer);
                    System.out.println("welcome to buyer panel");
                    showBuyerPanel(input, buyer, repository.getBuyerList(), repository.getAds());
                }
            } else if (firstPage == 4) {
                System.out.println("ads :");
                repository.getAds().forEach(System.out::println);
            }
            System.out.println("end 1 continue 0");
            back = input.nextInt();
        }
    }

    public static void showSellerPanel(Scanner input, Seller seller, ArrayList<Seller> sellers, ArrayList<Ad> newAds) {
        System.out.println("1 profile 2 add ad 3 available ads 4 History");
        int show = input.nextInt();
        switch (show) {
            case 1 -> {
                System.out.println(seller);
                System.out.println("if you want to change your username type your new username or type -1");
                String answer = input.next();
                if (!answer.equals("-1")) {
                    String finalAnswer = answer;
                    boolean check = sellers.stream().anyMatch(s -> s.getUsername().equals(finalAnswer));
                    while (check) {
                        System.out.println("enter another username");
                        answer = input.next();
                        String finalAnswer1 = answer;
                        check = sellers.stream().anyMatch(s -> s.getUsername().equals(finalAnswer1));
                    }
                    seller.setUsername(answer);
                }
                System.out.println("if you want to change your password type your new password or type -1");
                answer = input.next();
                if (!answer.equals("-1")) {
                    seller.setPassword(answer);
                }
                System.out.println("if you want to change your email type your new email or type -1");
                answer = input.next();
                if (!answer.equals("-1")) {
                    seller.setEmail(answer);
                }
                System.out.println("if you want to change your phonenumber type your new phonenumber or type -1");
                answer = input.next();
                if (!answer.equals("-1")) {
                    seller.setPhoneNumber(answer);
                }
                System.out.println("do you want to withdraw money from your wallet ? type yes");
                answer = input.next();
                if (answer.equalsIgnoreCase("yes")) {
                    System.out.println("enter how much");
                    seller.setWallet(seller.getWallet() - input.nextInt());
                    System.out.println("done");
                }
                break;
            }
            case 4 -> seller.getSalesAds().forEach(System.out::println);
            case 2 -> {
                System.out.println("enter category price name");
                Category category = switch (input.next().toUpperCase()) {
                    case "CAR" -> Category.CAR;
                    case "PHONE" -> Category.PHONE;
                    case "CLOTHS" -> Category.CLOTHES;
                    case "STATIONERY" -> Category.STATIONERY;
                    case "HOME_APPLIANCES" -> Category.HOME_APPLIANCES;
                    default -> null;
                };
                Ad newAd = new Ad(input.nextInt(), input.next(), seller, category);
                seller.getAvailableAds().add(newAd);
                newAds.add(newAd);
                break;
            }
            case 3 -> seller.getAvailableAds().forEach(ad -> {
                System.out.println(ad);
                if (newAds.stream().anyMatch(temp -> temp.equals(ad))) {
                    System.out.println("not confirmed");
                } else {
                    System.out.println("confirmed");
                }
            });
        }
    }

    public static void showMainAdminPanel(Scanner input, MainAdmin admin, ArrayList<Seller> sellers, ArrayList<Buyer> buyers, ArrayList<Ad> ads, ArrayList<Ad> newAds) {
        System.out.println("1 sellers 2 all ads 3 buyers 4 requests 5 pay admins and peiks");
        int show = input.nextInt();
        switch (show) {
            case 1:
                sellers.forEach(System.out::println);
                while (true) {
                    System.out.println("do you want to delete any seller? type yes or no");
                    if (input.next().equalsIgnoreCase("yes")) {
                        System.out.println("which one do you want to delete ?\nenter username :");
                        String username = input.next();
                        sellers.removeIf(seller -> seller.getUsername().equals(username));
                        System.out.println("removed");
                    } else {
                        break;
                    }
                }
                break;
            case 3:
                buyers.forEach(System.out::println);
                while (true) {
                    System.out.println("do you want to delete any buyer? type yes or no");
                    if (input.next().equalsIgnoreCase("yes")) {
                        System.out.println("which one do you want to delete ?\nenter username :");
                        String username = input.next();
                        buyers.removeIf(buyer -> buyer.getUsername().equals(username));
                        System.out.println("removed");
                    } else {
                        break;
                    }
                }
                break;
            case 2:
                ads.forEach(System.out::println);
                while (true) {
                    System.out.println("do you want to delete any ad? type yes or no");
                    if (input.next().equalsIgnoreCase("yes")) {
                        System.out.println("which one do you want to delete ?\nenter name :");
                        String name = input.next();
                        ads.removeIf(ad -> ad.getName().equals(name));
                        System.out.println("removed");
                    } else {
                        break;
                    }
                }
                break;
            case 4:
                ArrayList<Ad> temp = new ArrayList<>(newAds);
                for (Ad ad : temp) {
                    System.out.println(ad);
                    System.out.println("do you want to confirm ? type yes");
                    if (input.next().equalsIgnoreCase("yes")) {
                        ad.setStatus(Status.READY_TO_SALE);
                        ads.add(ad);
                        newAds.remove(ad);
                    } else {
                        newAds.remove(ad);
                    }
                    if (newAds.isEmpty()) {
                        break;
                    }
                }
                break;
            case 5:
                for (Peik peik : repository.getPeikList()) {
                    if (admin.getPeikMoney().containsKey(peik)) {
                        peik.setWallet(peik.getWallet() + admin.getPeikMoney().get(peik));
                        admin.getPeikMoney().remove(peik);
                        System.out.println("paid");
                    }
                }
                for (Admin miniAdmin : repository.getAdminList()) {
                    System.out.println(miniAdmin);
                    System.out.println("how much do you want to pay him ?");
                    int answer = input.nextInt();
                    miniAdmin.setWallet(miniAdmin.getWallet() + answer);
                    admin.setWallet(admin.getWallet() - answer);
                    System.out.println("paid");
                }
                System.out.println("done");
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    public static void showAdminPanel(Scanner input, ArrayList<Seller> sellers, ArrayList<Buyer> buyers, ArrayList<Ad> ads, ArrayList<Ad> newAds) {
        System.out.println("1 sellers 2 all ads 3 buyers 4 requests");
        int show = input.nextInt();
        switch (show) {
            case 1:
                sellers.forEach(System.out::println);
                sellers.removeIf(seller -> {
                    System.out.println("do you want to delete any seller? type yes or no");
                    return input.next().equalsIgnoreCase("yes");
                });
                break;
            case 3:
                buyers.forEach(System.out::println);
                buyers.removeIf(buyer -> {
                    System.out.println("do you want to delete any buyer? type yes or no");
                    return input.next().equalsIgnoreCase("yes");
                });
                break;
            case 2:
                ads.forEach(System.out::println);
                ads.removeIf(ad -> {
                    System.out.println("do you want to delete any ad? type yes or no");
                    return input.next().equalsIgnoreCase("yes");
                });
                break;
            case 4:
                ArrayList<Ad> temp = new ArrayList<>(newAds);
                temp.forEach(ad -> {
                    System.out.println(ad);
                    System.out.println("do you want to confirm ? type yes");
                    if (input.next().equalsIgnoreCase("yes")) {
                        ad.setStatus(Status.READY_TO_SALE);
                        ads.add(ad);
                        newAds.remove(ad);
                    } else {
                        newAds.remove(ad);
                    }
                });
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    public static void showBuyerPanel(Scanner input, Buyer buyer, ArrayList<Buyer> buyers, ArrayList<Ad> ads) {
        System.out.println("1 profile 2 saved Box 3 History 4 Sales ads 5 buy by ad name 6 buy by seller name");
        int show = input.nextInt();
        if (show == 1) {
            System.out.println(buyer);
            System.out.println("if you want to change your username type your new username or type -1");
            String answer = input.next();
            if (!answer.equals("-1")) {
                boolean check = false;
                for (Buyer buyerOne : buyers) {
                    if (buyer.getUsername().equals(buyerOne.getUsername())) {
                        check = true;
                        break;
                    }
                }
                while (check) {
                    System.out.println("enter another username");
                    answer = input.next();
                    for (Buyer buyerOne : buyers) {
                        if (buyer.getUsername().equals(buyerOne.getUsername())) {
                            check = true;
                            break;
                        }
                        check = false;
                    }
                }
                buyer.setUsername(answer);
            }
            System.out.println("if you want to change your password type your new password or type -1");
            answer = input.next();
            if (!answer.equals("-1")) {
                buyer.setPassword(answer);
            }
            System.out.println("if you want to change your email type your new email or type -1");
            answer = input.next();
            if (!answer.equals("-1")) {
                buyer.setEmail(answer);
            }
            System.out.println("if you want to change your phonenumber type your new phonenumber or type -1");
            answer = input.next();
            if (!answer.equals("-1")) {
                buyer.setPhoneNumber(answer);
            }
            System.out.println("do you want to charge your wallet ? type yes");
            answer = input.next();
            if (answer.equalsIgnoreCase("yes")) {
                System.out.println("enter how much");
                buyer.setWallet(buyer.getWallet() + input.nextInt());
                System.out.println("done");
            }
        } else if (show == 2) {
            if (buyer.getSavedAds() != null) {
                for (Ad ad : buyer.getSavedAds()) {
                    System.out.println(ad);
                    System.out.println("do you want to remove it ? type yes");
                    if (input.next().equalsIgnoreCase("yes")) {
                        buyer.getSavedAds().remove(ad);
                        break;
                    }
                    System.out.println("do you want to buy it ? type yes");
                    if (input.next().equalsIgnoreCase("yes")) {
                        buyer.setWallet(buyer.getWallet() - ad.getPrice());
                        ad.getSeller().setWallet(ad.getSeller().getWallet() + ad.getPrice());
                        ads.remove(ad);
                        buyer.getSalesAds().add(ad);
                        ad.getSeller().getSavedAds().add(ad);
                        System.out.println("done");
                    }
                    if (buyer.getSavedAds() == null) {
                        break;
                    }
                }
            }
        } else if (show == 3) {
            for (Ad ad : buyer.getSalesAds()) {
                System.out.println(ad);
            }
        } else if (show == 4) {
            System.out.println("1 sort by price 2 category 3 sort by price range 4 price range and category");
            int min, max;
            ArrayList<Ad> temp;
            String answer = input.next();
            switch (answer) {
                case "1" -> {
                    System.out.println("which one do you want ?");
                    ads.stream()
                            .sorted(new PriceComparator())
                            .forEach(ad -> {
                                System.out.println(ad);
                                System.out.println("do you want this ad ? type yes");
                                if (input.next().equalsIgnoreCase("yes")) {
                                    System.out.println("do you want to see seller reviews ? type yes");
                                    if (input.next().equals("yes")) {
                                        ad.getSeller().getReviews().forEach(review -> {
                                            System.out.println(review);
                                            System.out.println("type 1 if it's enough");
                                            if (input.nextInt() == 1) {
                                                return;
                                            }
                                        });
                                    }
                                    System.out.println("1 to buy 2 to save");
                                    if (input.nextInt() == 1) {
                                        buyer.setWallet(buyer.getWallet() - ad.getPrice());
                                        ad.getSeller().setWallet(ad.getSeller().getWallet() + (int) (0.9 * ad.getPrice()));
                                        repository.getAdmin().setWallet(repository.getAdmin().getWallet() + (int) (0.1 * ad.getPrice()));
                                        ads.remove(ad);
                                        buyer.getSalesAds().add(ad);
                                        ad.getSeller().getSalesAds().add(ad);
                                        System.out.println("do you want to get it by peik type yes");
                                        if (input.next().equals("yes")) {
                                            MainAdmin.peikPanel(repository, buyer, ad);
                                        } else {
                                            ad.setStatus(Status.WAITIING_TO_SEND);
                                        }
                                        System.out.println("do you want to write a review ? type yes");
                                        if (input.next().equals("yes")) {
                                            System.out.println("write :");
                                            String review = input.next();
                                            ad.getSeller().getReviews().add(review);
                                        }
                                        System.out.println("done");
                                        return;
                                    } else {
                                        buyer.getSavedAds().add(ad);
                                        System.out.println("done");
                                    }
                                }
                            });
                    break;
                }
                case "2" -> {
                    System.out.println("pick your category PHONE, CAR, CLOTHES, STATIONERY, HOME_APPLIANCES");
                    answer = input.next();
                    if (answer.equalsIgnoreCase("phone")) {
                        System.out.println("which one do you want ?");
                        for (Ad ad : ads) {
                            if (ad.getCategory().equals(Category.PHONE)) {
                                System.out.println(ad);
                                System.out.println("do you want this ad ? type yes");
                                if (input.next().equalsIgnoreCase("yes")) {
                                    System.out.println("do you want to see seller reviews ? type yes");
                                    if (input.next().equals("yes")) {
                                        for (String review : ad.getSeller().getReviews()) {
                                            System.out.println(review);
                                            System.out.println("type 1 if its enough");
                                            if (input.nextInt() == 1) {
                                                break;
                                            }
                                        }
                                    }
                                    System.out.println("1 to buy 2 to save");
                                    if (input.nextInt() == 1) {
                                        buyer.setWallet(buyer.getWallet() - ad.getPrice());
                                        ad.getSeller().setWallet(ad.getSeller().getWallet() + (int) (0.9 * ad.getPrice()));
                                        repository.getAdmin().setWallet(repository.getAdmin().getWallet() + (int) (0.1 * ad.getPrice()));
                                        ads.remove(ad);
                                        buyer.getSalesAds().add(ad);
                                        ad.getSeller().getSalesAds().add(ad);
                                        System.out.println("do you want to get it by peik type yes");
                                        if (input.next().equals("yes")) {
                                            MainAdmin.peikPanel(repository, buyer, ad);
                                        } else {
                                            ad.setStatus(Status.WAITIING_TO_SEND);
                                        }
                                        System.out.println("do you want to write a review ? type yes");
                                        if (input.next().equals("yes")) {
                                            System.out.println("write :");
                                            String review = input.next();
                                            ad.getSeller().getReviews().add(review);
                                        }
                                        System.out.println("done");
                                        break;
                                    } else {
                                        buyer.getSavedAds().add(ad);
                                        System.out.println("done");
                                    }
                                }
                            }
                            if (ads.size() < 1) {
                                break;
                            }
                        }
                    } else if (answer.equalsIgnoreCase("car")) {
                        for (Ad ad : ads) {
                            if (ad.getCategory().equals(Category.CAR)) {
                                System.out.println(ad);
                                System.out.println("do you want this ad ? type yes");
                                if (input.next().equalsIgnoreCase("yes")) {
                                    System.out.println("do you want to see seller reviews ? type yes");
                                    if (input.next().equals("yes")) {
                                        for (String review : ad.getSeller().getReviews()) {
                                            System.out.println(review);
                                            System.out.println("type 1 if its enough");
                                            if (input.nextInt() == 1) {
                                                break;
                                            }
                                        }
                                    }
                                    System.out.println("1 to buy 2 to save");
                                    if (input.nextInt() == 1) {
                                        buyer.setWallet(buyer.getWallet() - ad.getPrice());
                                        ad.getSeller().setWallet(ad.getSeller().getWallet() + (int) (0.9 * ad.getPrice()));
                                        repository.getAdmin().setWallet(repository.getAdmin().getWallet() + (int) (0.1 * ad.getPrice()));
                                        ads.remove(ad);
                                        buyer.getSalesAds().add(ad);
                                        ad.getSeller().getSalesAds().add(ad);
                                        System.out.println("do you want to get it by peik type yes");
                                        if (input.next().equals("yes")) {
                                            MainAdmin.peikPanel(repository, buyer, ad);
                                        } else {
                                            ad.setStatus(Status.WAITIING_TO_SEND);
                                        }
                                        System.out.println("done");
                                        break;
                                    } else {
                                        buyer.getSavedAds().add(ad);
                                        System.out.println("done");
                                    }
                                }
                            }
                            if (ads.size() < 1) {
                                break;
                            }
                        }
                    } else if (answer.equalsIgnoreCase("clothes")) {
                        for (Ad ad : ads) {
                            if (ad.getCategory().equals(Category.CLOTHES)) {
                                System.out.println(ad);
                                System.out.println("do you want this ad ? type yes");
                                if (input.next().equalsIgnoreCase("yes")) {
                                    System.out.println("do you want to see seller reviews ? type yes");
                                    if (input.next().equals("yes")) {
                                        for (String review : ad.getSeller().getReviews()) {
                                            System.out.println(review);
                                            System.out.println("type 1 if its enough");
                                            if (input.nextInt() == 1) {
                                                break;
                                            }
                                        }
                                    }
                                    System.out.println("1 to buy 2 to save");
                                    if (input.nextInt() == 1) {
                                        buyer.setWallet(buyer.getWallet() - ad.getPrice());
                                        ad.getSeller().setWallet(ad.getSeller().getWallet() + (int) (0.9 * ad.getPrice()));
                                        repository.getAdmin().setWallet(repository.getAdmin().getWallet() + (int) (0.1 * ad.getPrice()));
                                        ads.remove(ad);
                                        buyer.getSalesAds().add(ad);
                                        ad.getSeller().getSalesAds().add(ad);
                                        System.out.println("do you want to get it by peik type yes");
                                        if (input.next().equals("yes")) {
                                            MainAdmin.peikPanel(repository, buyer, ad);
                                        } else {
                                            ad.setStatus(Status.WAITIING_TO_SEND);
                                        }
                                        System.out.println("done");
                                        break;
                                    } else {
                                        buyer.getSavedAds().add(ad);
                                        System.out.println("done");
                                    }
                                }
                            }
                            if (ads.size() < 1) {
                                break;
                            }
                        }
                    } else if (answer.equalsIgnoreCase("stationery")) {
                        for (Ad ad : ads) {
                            if (ad.getCategory().equals(Category.STATIONERY)) {
                                System.out.println(ad);
                                System.out.println("do you want this ad ? type yes");
                                if (input.next().equalsIgnoreCase("yes")) {
                                    System.out.println("do you want to see seller reviews ? type yes");
                                    if (input.next().equals("yes")) {
                                        for (String review : ad.getSeller().getReviews()) {
                                            System.out.println(review);
                                            System.out.println("type 1 if its enough");
                                            if (input.nextInt() == 1) {
                                                break;
                                            }
                                        }
                                    }
                                    System.out.println("1 to buy 2 to save");
                                    if (input.nextInt() == 1) {
                                        buyer.setWallet(buyer.getWallet() - ad.getPrice());
                                        ad.getSeller().setWallet(ad.getSeller().getWallet() + (int) (0.9 * ad.getPrice()));
                                        repository.getAdmin().setWallet(repository.getAdmin().getWallet() + (int) (0.1 * ad.getPrice()));
                                        ads.remove(ad);
                                        buyer.getSalesAds().add(ad);
                                        ad.getSeller().getSalesAds().add(ad);
                                        System.out.println("do you want to get it by peik type yes");
                                        if (input.next().equals("yes")) {
                                            MainAdmin.peikPanel(repository, buyer, ad);
                                        } else {
                                            ad.setStatus(Status.WAITIING_TO_SEND);
                                        }
                                        System.out.println("done");
                                        break;
                                    } else {
                                        buyer.getSavedAds().add(ad);
                                        System.out.println("done");
                                    }
                                }
                            }
                            if (ads.size() < 1) {
                                break;
                            }
                        }
                    } else if (answer.equalsIgnoreCase("HOME APPLIANCES")) {
                        for (Ad ad : ads) {
                            if (ad.getCategory().equals(Category.HOME_APPLIANCES)) {
                                System.out.println(ad);
                                System.out.println("do you want this ad ? type yes");
                                if (input.next().equalsIgnoreCase("yes")) {
                                    System.out.println("do you want to see seller reviews ? type yes");
                                    if (input.next().equals("yes")) {
                                        for (String review : ad.getSeller().getReviews()) {
                                            System.out.println(review);
                                            System.out.println("type 1 if its enough");
                                            if (input.nextInt() == 1) {
                                                break;
                                            }
                                        }
                                    }
                                    System.out.println("1 to buy 2 to save");
                                    if (input.nextInt() == 1) {
                                        buyer.setWallet(buyer.getWallet() - ad.getPrice());
                                        ad.getSeller().setWallet(ad.getSeller().getWallet() + (int) (0.9 * ad.getPrice()));
                                        repository.getAdmin().setWallet(repository.getAdmin().getWallet() + (int) (0.1 * ad.getPrice()));
                                        ads.remove(ad);
                                        buyer.getSalesAds().add(ad);
                                        ad.getSeller().getSalesAds().add(ad);
                                        System.out.println("do you want to get it by peik type yes");
                                        if (input.next().equals("yes")) {
                                            MainAdmin.peikPanel(repository, buyer, ad);
                                        } else {
                                            ad.setStatus(Status.WAITIING_TO_SEND);
                                        }
                                        System.out.println("done");
                                        break;
                                    } else {
                                        buyer.getSavedAds().add(ad);
                                        System.out.println("done");
                                    }
                                }
                            }
                            if (ads.size() < 1) {
                                break;
                            }
                        }
                    }
                }
                case "3" -> {
                    System.out.println("enter min then max price :");
                    min = input.nextInt();
                    max = input.nextInt();
                    temp = ads.stream()
                            .filter(ad -> ad.getPrice() >= min && ad.getPrice() <= max)
                            .sorted(new PriceComparator())
                            .collect(Collectors.toCollection(ArrayList::new));
                    System.out.println("which one do you want ?");
                    temp.forEach(ad -> {
                        System.out.println(ad);
                        System.out.println("do you want this ad ? type yes");
                        if (input.next().equalsIgnoreCase("yes")) {
                            System.out.println("do you want to see seller reviews ? type yes");
                            if (input.next().equals("yes")) {
                                ad.getSeller().getReviews().forEach(review -> {
                                    System.out.println(review);
                                    System.out.println("type 1 if its enough");
                                    if (input.nextInt() == 1) {
                                        return;
                                    }
                                });
                            }
                            System.out.println("1 to buy 2 to save");
                            if (input.nextInt() == 1) {
                                buyer.setWallet(buyer.getWallet() - ad.getPrice());
                                ad.getSeller().setWallet(ad.getSeller().getWallet() + (int) (0.9 * ad.getPrice()));
                                repository.getAdmin().setWallet(repository.getAdmin().getWallet() + (int) (0.1 * ad.getPrice()));
                                ads.remove(ad);
                                buyer.getSalesAds().add(ad);
                                ad.getSeller().getSalesAds().add(ad);
                                System.out.println("do you want to get it by peik type yes");
                                if (input.next().equals("yes")) {
                                    MainAdmin.peikPanel(repository, buyer, ad);
                                } else {
                                    ad.setStatus(Status.WAITIING_TO_SEND);
                                }
                                System.out.println("do you want to write a review ? type yes");
                                if (input.next().equals("yes")) {
                                    System.out.println("write :");
                                    String review = input.next();
                                    ad.getSeller().getReviews().add(review);
                                }
                                System.out.println("done");
                                return;
                            } else {
                                buyer.getSavedAds().add(ad);
                                System.out.println("done");
                            }
                        }
                    });
                    break;
                }
                case "4" -> {
                    System.out.println("enter min then max price :");
                    min = input.nextInt();
                    max = input.nextInt();
                    temp = new ArrayList<>();
                    for (Ad ad : ads) {
                        if (ad.getPrice() >= min && ad.getPrice() <= max) {
                            temp.add(ad);
                        }
                    }
                    temp.sort(new PriceComparator());
                    System.out.println("now pick your category PHONE, CAR, CLOTHES, STATIONERY, HOME_APPLIANCES");
                    answer = input.next();
                    if (answer.equalsIgnoreCase("phone")) {
                        System.out.println("which one do you want ?");
                        for (Ad ad : temp) {
                            if (ad.getCategory().equals(Category.PHONE)) {
                                System.out.println(ad);
                                System.out.println("do you want this ad ? type yes");
                                if (input.next().equalsIgnoreCase("yes")) {
                                    System.out.println("do you want to see seller reviews ? type yes");
                                    if (input.next().equals("yes")) {
                                        for (String review : ad.getSeller().getReviews()) {
                                            System.out.println(review);
                                            System.out.println("type 1 if its enough");
                                            if (input.nextInt() == 1) {
                                                break;
                                            }
                                        }
                                    }
                                    System.out.println("1 to buy 2 to save");
                                    if (input.nextInt() == 1) {
                                        buyer.setWallet(buyer.getWallet() - ad.getPrice());
                                        ad.getSeller().setWallet(ad.getSeller().getWallet() + (int) (0.9 * ad.getPrice()));
                                        repository.getAdmin().setWallet(repository.getAdmin().getWallet() + (int) (0.1 * ad.getPrice()));
                                        ads.remove(ad);
                                        buyer.getSalesAds().add(ad);
                                        ad.getSeller().getSalesAds().add(ad);
                                        System.out.println("do you want to get it by peik type yes");
                                        if (input.next().equals("yes")) {
                                            MainAdmin.peikPanel(repository, buyer, ad);
                                        } else {
                                            ad.setStatus(Status.WAITIING_TO_SEND);
                                        }
                                        System.out.println("do you want to write a review ? type yes");
                                        if (input.next().equals("yes")) {
                                            System.out.println("write :");
                                            String review = input.next();
                                            ad.getSeller().getReviews().add(review);
                                        }
                                        System.out.println("done");
                                        break;
                                    } else {
                                        buyer.getSavedAds().add(ad);
                                        System.out.println("done");
                                    }
                                }
                            }
                            if (ads.size() < 1) {
                                break;
                            }
                        }
                    } else if (answer.equalsIgnoreCase("car")) {
                        for (Ad ad : temp) {
                            if (ad.getCategory().equals(Category.CAR)) {
                                System.out.println(ad);
                                System.out.println("do you want this ad ? type yes");
                                if (input.next().equalsIgnoreCase("yes")) {
                                    System.out.println("do you want to see seller reviews ? type yes");
                                    if (input.next().equals("yes")) {
                                        for (String review : ad.getSeller().getReviews()) {
                                            System.out.println(review);
                                            System.out.println("type 1 if its enough");
                                            if (input.nextInt() == 1) {
                                                break;
                                            }
                                        }
                                    }
                                    System.out.println("1 to buy 2 to save");
                                    if (input.nextInt() == 1) {
                                        buyer.setWallet(buyer.getWallet() - ad.getPrice());
                                        ad.getSeller().setWallet(ad.getSeller().getWallet() + (int) (0.9 * ad.getPrice()));
                                        repository.getAdmin().setWallet(repository.getAdmin().getWallet() + (int) (0.1 * ad.getPrice()));
                                        ads.remove(ad);
                                        buyer.getSalesAds().add(ad);
                                        ad.getSeller().getSalesAds().add(ad);
                                        System.out.println("do you want to get it by peik type yes");
                                        if (input.next().equals("yes")) {
                                            MainAdmin.peikPanel(repository, buyer, ad);
                                        } else {
                                            ad.setStatus(Status.WAITIING_TO_SEND);
                                        }
                                        System.out.println("done");
                                        break;
                                    } else {
                                        buyer.getSavedAds().add(ad);
                                        System.out.println("done");
                                    }
                                }
                            }
                            if (ads.size() < 1) {
                                break;
                            }
                        }
                    } else if (answer.equalsIgnoreCase("clothes")) {
                        for (Ad ad : temp) {
                            if (ad.getCategory().equals(Category.CLOTHES)) {
                                System.out.println(ad);
                                System.out.println("do you want this ad ? type yes");
                                if (input.next().equalsIgnoreCase("yes")) {
                                    System.out.println("do you want to see seller reviews ? type yes");
                                    if (input.next().equals("yes")) {
                                        for (String review : ad.getSeller().getReviews()) {
                                            System.out.println(review);
                                            System.out.println("type 1 if its enough");
                                            if (input.nextInt() == 1) {
                                                break;
                                            }
                                        }
                                    }
                                    System.out.println("1 to buy 2 to save");
                                    if (input.nextInt() == 1) {
                                        buyer.setWallet(buyer.getWallet() - ad.getPrice());
                                        ad.getSeller().setWallet(ad.getSeller().getWallet() + (int) (0.9 * ad.getPrice()));
                                        repository.getAdmin().setWallet(repository.getAdmin().getWallet() + (int) (0.1 * ad.getPrice()));
                                        ads.remove(ad);
                                        buyer.getSalesAds().add(ad);
                                        ad.getSeller().getSalesAds().add(ad);
                                        System.out.println("do you want to get it by peik type yes");
                                        if (input.next().equals("yes")) {
                                            MainAdmin.peikPanel(repository, buyer, ad);
                                        } else {
                                            ad.setStatus(Status.WAITIING_TO_SEND);
                                        }
                                        System.out.println("done");
                                        break;
                                    } else {
                                        buyer.getSavedAds().add(ad);
                                        System.out.println("done");
                                    }
                                }
                            }
                            if (ads.size() < 1) {
                                break;
                            }
                        }
                    } else if (answer.equalsIgnoreCase("stationery")) {
                        for (Ad ad : temp) {
                            if (ad.getCategory().equals(Category.STATIONERY)) {
                                System.out.println(ad);
                                System.out.println("do you want this ad ? type yes");
                                if (input.next().equalsIgnoreCase("yes")) {
                                    System.out.println("do you want to see seller reviews ? type yes");
                                    if (input.next().equals("yes")) {
                                        for (String review : ad.getSeller().getReviews()) {
                                            System.out.println(review);
                                            System.out.println("type 1 if its enough");
                                            if (input.nextInt() == 1) {
                                                break;
                                            }
                                        }
                                    }
                                    System.out.println("1 to buy 2 to save");
                                    if (input.nextInt() == 1) {
                                        buyer.setWallet(buyer.getWallet() - ad.getPrice());
                                        ad.getSeller().setWallet(ad.getSeller().getWallet() + (int) (0.9 * ad.getPrice()));
                                        repository.getAdmin().setWallet(repository.getAdmin().getWallet() + (int) (0.1 * ad.getPrice()));
                                        ads.remove(ad);
                                        buyer.getSalesAds().add(ad);
                                        ad.getSeller().getSalesAds().add(ad);
                                        System.out.println("do you want to get it by peik type yes");
                                        if (input.next().equals("yes")) {
                                            MainAdmin.peikPanel(repository, buyer, ad);
                                        } else {
                                            ad.setStatus(Status.WAITIING_TO_SEND);
                                        }
                                        System.out.println("done");
                                        break;
                                    } else {
                                        buyer.getSavedAds().add(ad);
                                        System.out.println("done");
                                    }
                                }
                            }
                            if (ads.size() < 1) {
                                break;
                            }
                        }
                    } else if (answer.equalsIgnoreCase("HOME APPLIANCES")) {
                        for (Ad ad : temp) {
                            if (ad.getCategory().equals(Category.HOME_APPLIANCES)) {
                                System.out.println(ad);
                                System.out.println("do you want this ad ? type yes");
                                if (input.next().equalsIgnoreCase("yes")) {
                                    System.out.println("do you want to see seller reviews ? type yes");
                                    if (input.next().equals("yes")) {
                                        for (String review : ad.getSeller().getReviews()) {
                                            System.out.println(review);
                                            System.out.println("type 1 if its enough");
                                            if (input.nextInt() == 1) {
                                                break;
                                            }
                                        }
                                    }
                                    System.out.println("1 to buy 2 to save");
                                    if (input.nextInt() == 1) {
                                        buyer.setWallet(buyer.getWallet() - ad.getPrice());
                                        ad.getSeller().setWallet(ad.getSeller().getWallet() + (int) (0.9 * ad.getPrice()));
                                        repository.getAdmin().setWallet(repository.getAdmin().getWallet() + (int) (0.1 * ad.getPrice()));
                                        ads.remove(ad);
                                        buyer.getSalesAds().add(ad);
                                        ad.getSeller().getSalesAds().add(ad);
                                        System.out.println("do you want to get it by peik type yes");
                                        if (input.next().equals("yes")) {
                                            MainAdmin.peikPanel(repository, buyer, ad);
                                        } else {
                                            ad.setStatus(Status.WAITIING_TO_SEND);
                                        }
                                        System.out.println("done");
                                        break;
                                    } else {
                                        buyer.getSavedAds().add(ad);
                                        System.out.println("done");
                                    }
                                }
                            }
                            if (ads.size() < 1) {
                                break;
                            }
                        }
                    }
                }
            }
        } else if (show == 5) {
            System.out.println("enter ad name :");
            String name = input.next();
            Optional<Ad> optionalAd = repository.getAds().stream().filter(ad -> ad.getName().equals(name)).findFirst();
            optionalAd.ifPresent(ad -> {
                System.out.println(ad);
                buyer.setWallet(buyer.getWallet() - ad.getPrice());
                ad.getSeller().setWallet(ad.getSeller().getWallet() + (int) (0.9 * ad.getPrice()));
                repository.getAdmin().setWallet(repository.getAdmin().getWallet() + (int) (0.1 * ad.getPrice()));
                ads.remove(ad);
                buyer.getSalesAds().add(ad);
                ad.getSeller().getSalesAds().add(ad);
                System.out.println("do you want to get it by peik type yes");
                if (input.next().equals("yes")) {
                    MainAdmin.peikPanel(repository, buyer, ad);
                } else {
                    ad.setStatus(Status.WAITIING_TO_SEND);
                }
                System.out.println("done");
            });
        } else if (show == 6) {
            System.out.println("enter seller name :");
            String name = input.next();
            Optional<Seller> optionalSeller = repository.getSellerList().stream().filter(seller -> seller.getUsername().equals(name)).findFirst();
            optionalSeller.ifPresent(seller -> {
                seller.getAvailableAds().forEach(ad -> {
                    System.out.println(ad);
                    System.out.println("you want this one ? type yes");
                    if (input.next().equals("yes")) {
                        buyer.setWallet(buyer.getWallet() - ad.getPrice());
                        ad.getSeller().setWallet(ad.getSeller().getWallet() + (int) (0.9 * ad.getPrice()));
                        repository.getAdmin().setWallet(repository.getAdmin().getWallet() + (int) (0.1 * ad.getPrice()));
                        ads.remove(ad);
                        buyer.getSalesAds().add(ad);
                        ad.getSeller().getSalesAds().add(ad);
                        System.out.println("do you want to get it by peik type yes");
                        if (input.next().equals("yes")) {
                            MainAdmin.peikPanel(repository, buyer, ad);
                        } else {
                            ad.setStatus(Status.WAITIING_TO_SEND);
                        }
                        System.out.println("done");
                    }
                });
            });
        }

    }
}