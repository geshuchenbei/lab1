import java.io.*;
import java.util.*;
public class Main {

	public static boolean isConstant(char x){
		if (x>='0' && x<='9')	return true;
		else return false;
	}
	public static boolean isVar(char x){
		if (x>='a' && x<='z')	return true;
		else return false;
	}

	public static boolean allInteger(String x){
		for(int i=0;i<x.length();i++){
			if (!isConstant(x.charAt(i)))	return false;
		}
		return true;
	}
	public static String expression(String buf){
		return new String(buf);
	}
	public static String simplify(String buf,String cmd){


//		System.out.println(cmd);

		Map vtoc = new HashMap<>();


		int n=cmd.length();
		String vname = new String();
		int ind=10;
		while (ind<n){
			if (cmd.charAt(ind)==' ' || cmd.charAt(ind)=='=')	;
			else if (isVar(cmd.charAt(ind)))	vname=String.valueOf(cmd.charAt(ind));
			else{
				int id=ind;
				int val=0;
				while (id<n){
					if (isConstant(cmd.charAt(id))){
						val*=10;
						val+=Integer.valueOf(cmd.charAt(id))-Integer.valueOf('0');
						id++;
					}else{
						break;
					}
				}
				ind=id-1;
				vtoc.put(vname,val);
			}
			ind++;
		}

		
//		System.out.println(buf);
		String[] ta=buf.split("\\+");
		String bigans= new String();
		for(int i=0;i<ta.length;i++){
			String ans = new String();
			String temp=ta[i];
			String[] tb=temp.split("\\*");
			int vans=1;
			for(int j=0;j<tb.length;j++){
				if (allInteger(tb[j])){
					vans*=Integer.parseInt(tb[j]);
				}else{
					if (vtoc.containsKey(tb[j])){
						int tp=Integer.parseInt(String.valueOf(vtoc.get(tb[j])));
						vans*=tp;
					}else{
						if (ans.length()==0)	ans=tb[j];
						else ans+="*"+tb[j];
					}
				}
		//		System.out.println(tb[j]);
			}
			if (vans!=1)	if (ans.length()==0)	ans=String.valueOf(vans);
			else ans=String.valueOf(vans)+"*"+ans;
			if (ans.length()==0)	ans="1";
			bigans+=ans+"+";
		}
	//	System.out.println(bigans);
		String[] tc=bigans.split("\\+");
		String finans= new String();
		int finv=0;
		for(int i=0;i<tc.length;i++){
		//	if (tc[i].length()==0)	continue;
			if (allInteger(tc[i])){
				finv+=Integer.parseInt(tc[i]);
			}else{
				if (finans.length()==0)	finans=tc[i];
				else finans+="+"+tc[i];
			}
		}
		if (finv!=0)	if (finans.length()==0)	finans=String.valueOf(finv);
		else finans=String.valueOf(finv)+"+"+finans;




		return finans;
	}

	public static String derivative(String buf,String cmd){
		

		String cname=String.valueOf(cmd.charAt(5));
		

		String finans = new String("");
		String[] ta=buf.split("\\+");
		boolean appear=false;
		for(int i=0;i<ta.length;i++){
			int cnt=0;
			String[] tb=ta[i].split("\\*");
			for(int j=0;j<tb.length;j++){
				if (tb[j].equals(cname))	cnt++;
				//System.out.println(tb[j]);
			}
			String tempans=new String("");
			if (cnt!=0){
				boolean mark=false;
				for(int j=0;j<tb.length;j++){
					if (mark==false && tb[j].equals(cname)){
						mark=true;
						appear=true;
						continue;
					}
					if (tempans.length()==0)	tempans=tb[j];
					else tempans+="*"+tb[j];
				}
				if (cnt!=1)	if (tempans.length()==0)	tempans=String.valueOf(cnt);
				else tempans=String.valueOf(cnt)+"*"+tempans;
			}
	//		System.out.println(tempans);
			if (tempans.length()==0)	continue;
			if (finans.length()==0)	finans=tempans;
			else finans+="+"+tempans;
		}

		if (!appear)	return "error!";


		return finans;

	}

	public static void main(String args[]){
		Scanner cin = new Scanner(new BufferedInputStream(System.in));
		String suf = new String();
		while (cin.hasNext()){
			String buf=cin.nextLine();
			if (buf.charAt(0)!='!'){
				System.out.println(buf);//print expression
				suf=expression(buf);
			}else{
				if (buf.charAt(1)=='s'){//simplify
				//	System.out.println(buf);
					String temp=simplify(suf,buf);
					System.out.println(temp);
				}else{
				//	System.out.println(buf);
					String temp=derivative(suf,buf);
					System.out.println(temp);
				}
			}
		}
	}
}

