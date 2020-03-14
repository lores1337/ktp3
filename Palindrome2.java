public class Palindrome {
    public static void main(String[] args) {
        String s="";
        for (int i = 0; i < args.length; i++) {
            s = args[i];
            System.out.println(isPalindrome(s));
        }
    }
    public static String reverseString(String s){
        String str="";
        for(int i =s.length()-1;i>=0;i--)
            str+=s.charAt(i);
        return str;
    }
    public static boolean isPalindrome(String s){
        return s.equals(reverseString(s));
    }
}
