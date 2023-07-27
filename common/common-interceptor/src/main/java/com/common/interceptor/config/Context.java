package com.common.interceptor.config;

import java.util.ArrayDeque;
import java.util.Deque;

public class Context {
    public static void main(String[] args) {
        int[] height = new int[]{8,2,8,9,0,1,7,7,9};
        int n = height.length;
        int[] arr = new int[n];
        Deque<int[]> q = new ArrayDeque<>();
        int i = 0;
        while(height[i] == 0)
            i++;
        q.offer(new int[]{i,height[i],0});
        i++;
        for(;i<n;i++){
            if(height[i] == 0){
                arr[i] = arr[i-1];
                continue;
            }
            int[] m = q.peekLast();
            int stone = 0;
            if(m[1] > height[i]){
                arr[i] = arr[m[0]]+(i-m[0]-1)*height[i];
            }else{
                while(!q.isEmpty()&&q.peekLast()[1]<=height[i]){
                    m = q.pollLast();
                    int t = (i-m[0]-1)*m[1];
                    if(t>0){
                        arr[i] = arr[m[0]]+t-stone;
                    }else
                        arr[i] = arr[i-1];
                    stone+=m[1]+m[2];
                }
                if(!q.isEmpty()){
                    m = q.peekLast();
                    int t = (i-m[0]-1)*height[i];
                    if(t>0){
                        arr[i] = arr[m[0]]+t-stone;
                    }
                }else
                    stone = 0;
            }
            q.addLast(new int[]{i,height[i],stone});
        }
        System.out.println(arr[n-1]);
    }
}
