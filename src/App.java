// 6510405521 Thatpong Wongchaita

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class App {
  public static void main(String[] args) throws Exception {
    User user = new User("Billy", "billy@gmail.com", 23);
    List<Validator> validators = Arrays.asList(
        new EmailValidator(),
        new AgeValidator(),
        new NameValidator());
    Authenticator auth = new Authenticator();
    auth.register(user, validators);
  }
}

class User {
  private String name;
  private String email;
  private int age;

  public User(String name, String email, int age) {
    this.name = name;
    this.email = email;
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public int getAge() {
    return age;
  }

}

interface Validator {
  public void validate(User user);
}

class NameValidator implements Validator {
  public NameValidator() {
  }

  public void validate(User user) {
    if (user.getName() == null || user.getName().trim().equals("")) {
      throw new IllegalArgumentException("Name should not empty");
    }
    if (!user.getName().matches("[a-zA-Z]+")) {
      throw new IllegalArgumentException("Name is wrong format");
    }
  }
}

class EmailValidator implements Validator {
  public EmailValidator() {
  }

  public void validate(User user) {
    if (user.getEmail() == null || user.getEmail().trim().equals("")) {
      throw new IllegalArgumentException("Email should not empty");
    }
    String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    Pattern validEmailPattern = Pattern.compile(EMAIL_PATTERN);
    if (!validEmailPattern.matcher(user.getEmail()).matches()) {
      throw new IllegalArgumentException("Email is wrong format");
    }
    List<String> notAllowDomains = Arrays.asList("dom1.cc", "dom2.cc", "dom3.cc");
    if (notAllowDomains.contains(user.getEmail().split("@")[1])) {
      throw new IllegalArgumentException("Domain Email is not allow");
    }
  }
}

class AgeValidator implements Validator {
  public AgeValidator() {
  }

  public void validate(User user) {
    if (user.getAge() < 20) {
      throw new IllegalArgumentException("Age should more than 20 years");
    }
  }
}

class Authenticator {
  public Authenticator() {
  }

  public boolean register(User user, List<Validator> validators) {
    for (Validator val : validators) {
      val.validate(user);
    }
    return true;
  }
}
