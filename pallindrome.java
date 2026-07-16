class Solution {
    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }

        int temp = x;
        int sum = 0;

        while (x != 0) {
            int digit = x % 10;
            sum = sum * 10 + digit;
            x = x / 10;
        }

        return temp == sum;
    }
}