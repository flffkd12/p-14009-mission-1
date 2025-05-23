import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Proverb {

  public static void main(String[] args) {
    System.out.println("== 명언 앱 ==");
    Scanner scanner = new Scanner(System.in);

    int provCnt = 0;
    List<String> provList = new ArrayList<>();
    provList.add("");
    List<String> authorList = new ArrayList<>();
    authorList.add("");
    TreeSet<Integer> listCheckSet = new TreeSet<>();

    label:
    while (true) {
      System.out.print("명령) ");
      String cmd = scanner.nextLine().trim();
      String cmdType = cmd.substring(0, 2);

      switch (cmdType) {

        // 1단계
        case "종료":
          break label;

        // 2~4단계
        case "등록":
          provCnt = makeProv(provCnt, provList, authorList, listCheckSet, scanner);
          break;

        // 5단계
        case "목록":
          getAllProv(provList, authorList, listCheckSet);
          break;

        // 6,7단계
        case "삭제":
          int removeId = Integer.parseInt(cmd.split("=")[1]);
          deleteProv(removeId, provList, authorList, listCheckSet);
          break;

        // 8단계
        case "수정":
          int modifyId = Integer.parseInt(cmd.split("=")[1]);
          modifyProv(modifyId, provList, authorList, listCheckSet, scanner);
          break;
      }
    }

    scanner.close();
  }

  private static int makeProv(
      int provCnt,
      List<String> provList,
      List<String> authorList,
      Set<Integer> listCheckSet,
      Scanner scanner
  ) {
    while (true) {
      System.out.print("명언 : ");
      String author = scanner.nextLine().trim();
      if (containsSpecificChar(author)) {
        provList.add(author);

        while (true) {
          System.out.print("작가 : ");
          author = scanner.nextLine().trim();
          if (containsSpecificChar(author)) {
            authorList.add(author);
            int curProvCnt = provCnt + 1;
            listCheckSet.add(curProvCnt);
            System.out.println(curProvCnt + "번 명언이 등록되었습니다.");
            return curProvCnt;
          }

          System.out.println("작가에 특수문자를 제외하고 입력해주세요.");
        }
      }

      System.out.println("명언에 특수문자를 제외하고 입력해주세요.");
    }
  }

  private static void getAllProv(
      List<String> provList,
      List<String> authorList,
      TreeSet<Integer> listCheckSet
  ) {
    System.out.println("번호 / 작가 / 명언");
    System.out.println("----------------");

    for (int idx : listCheckSet) {
      System.out.println(idx + " / " + authorList.get(idx) + " / " + provList.get(idx));
    }
  }

  private static void deleteProv(
      int removeId,
      List<String> provList,
      List<String> authorList,
      TreeSet<Integer> listCheckSet
  ) {
    if (listCheckSet.contains(removeId)) {
      provList.set(removeId, "");
      authorList.set(removeId, "");
      listCheckSet.remove(removeId);
      System.out.println(removeId + "번 명언이 삭제되었습니다.");
    } else {
      System.out.println(removeId + "번 명언은 존재하지 않습니다.");
    }
  }

  private static void modifyProv(
      int modifyId,
      List<String> provList,
      List<String> authorList,
      TreeSet<Integer> listCheckSet,
      Scanner scanner
  ) {
    if (listCheckSet.contains(modifyId)) {
      System.out.println("명언(기존) : " + provList.get(modifyId));

      while (true) {
        System.out.print("명언 : ");
        String newAuthor = scanner.nextLine().trim();
        if (containsSpecificChar(newAuthor)) {
          provList.set(modifyId, newAuthor);
          System.out.println("작가(기존) : " + authorList.get(modifyId));

          while (true) {
            System.out.print("작가 : ");
            newAuthor = scanner.nextLine().trim();
            if (containsSpecificChar(newAuthor)) {
              authorList.set(modifyId, newAuthor);
              return;
            }

            System.out.println("명언에 특수문자를 제외하고 입력해주세요.");
          }
        }

        System.out.println("명언에 특수문자를 제외하고 입력해주세요.");
      }
    } else {
      System.out.println(modifyId + "번 명언은 존재하지 않습니다.");
    }
  }

  private static boolean containsSpecificChar(String text) {
    Pattern pattern = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?`~]");
    Matcher matcher = pattern.matcher(text);
    return !matcher.find();
  }
}
