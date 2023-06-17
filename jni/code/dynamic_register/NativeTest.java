public class NativeTest { 

   static {
      System.loadLibrary("nativetest"); 
   }
 
   private native void init();

   private native void init(int age);

   private native boolean init(String name);

   private native void update();
 
   public static void main(String[] args) {
      NativeTest nativeTest = new NativeTest();
      nativeTest.init();
      nativeTest.init(2);
      nativeTest.init("test");
      nativeTest.update();
   }
}