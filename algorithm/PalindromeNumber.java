class Solution {
    public boolean isPalindrome(int x) {
        
        if(x<0){
            return false;
        }
        
        int t=x;
        int r=0;
        int rev=0;
        while(t!=0){
            r=t%10;
            t=t/10;
            rev = rev*10+r;
        }
        
        if(x==rev){
            return true;
        }else{
            return false;
        }
        
    }
}