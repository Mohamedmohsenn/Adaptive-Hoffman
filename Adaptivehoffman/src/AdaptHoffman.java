import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

class BTNode {

	BTNode right;
	BTNode left;
	char nodeSymbol;
	int symbolCount;
	String nodeCode;
	int nodeNumber;

	public BTNode() {
		right = left = null;
	}

}

class AdaptHoffmanOperations {
	BTNode root = new BTNode();
	BTNode tmp = new BTNode();
	BTNode parent = new BTNode();
	ArrayList<Character> dic = new ArrayList<Character>();
	public ArrayList<String> finalCode = new ArrayList<String>();
	ArrayList<BTNode> linearTree3 = new ArrayList<BTNode>();
	ArrayList<BTNode> linearTree2 = new ArrayList<BTNode>();
	ArrayList<BTNode> linearTree = new ArrayList<BTNode>();
	ArrayList<BTNode> big = new ArrayList<BTNode>();
	private String decompressed = "";

	public AdaptHoffmanOperations() {
		root.nodeNumber = 100;
	}

	public BTNode getRoot() {
		return root;
	}

	void search(BTNode node) {
		if (node == null) {
			return;
		}

		search(node.left);

		search(node.right);

		if (node.nodeSymbol == '*' && node.right == null && node.left == null) {
			tmp = node;
			return;
		}
	}

	void search(BTNode node, char chr) {
		if (node == null) {
			return;
		}

		search(node.left, chr);

		search(node.right, chr);

		if (node.nodeSymbol == chr) {
			tmp = node;
			node.symbolCount++;
			BTNode cur = root;
			BTNode n = node;
			while (n != cur) {
				getParent(cur, n);
				n = parent;
				n.symbolCount++;
			}
			return;
		}
	}

	void getParent(BTNode node, BTNode n) {
		if (node == null) {
			return;
		}

		getParent(node.left, n);

		getParent(node.right, n);

		if ((node.right == n || node.left == n) && node != null) {
			parent = node;
			return;
		}
	}

	void traverse2(BTNode node) {
		if (node == null) {
			return;
		}

		traverse2(node.left);

		traverse2(node.right);

		linearTree2.add(node);
	}

	void traverse(BTNode node) {
		if (node == null) {
			return;
		}

		traverse(node.left);

		traverse(node.right);
		if (node.right != null && node.left != null) {
			if (node.right.symbolCount < node.left.symbolCount)
				linearTree.add(node);
		}
	}

	void traverse3(BTNode node) {
		if (node == null) {
			return;
		}

		traverse3(node.left);

		traverse3(node.right);

		linearTree3.add(node);

	}

	void checkSwap(char x, char y) {
		int swapsNumber = 0;
		linearTree.clear();
		traverse(root);
		for (int i = 0; i < linearTree.size(); i++) {
			if (linearTree.get(i).left.right == null
					&& linearTree.get(i).left.left == null) {
				x = '0';
				y = 'l';
			}
			// update node numbers
			int tem = linearTree.get(i).right.nodeNumber;
			linearTree.get(i).right.nodeNumber = linearTree.get(i).left.nodeNumber;
			linearTree.get(i).left.nodeNumber = tem;

			// swap
			BTNode temp = linearTree.get(i).right;
			linearTree.get(i).right = linearTree.get(i).left;
			linearTree.get(i).left = temp;

			// update node code :
			// first swap
			String te = linearTree.get(i).right.nodeCode;
			linearTree.get(i).right.nodeCode = linearTree.get(i).left.nodeCode;
			linearTree.get(i).left.nodeCode = te;

			// second edit by the algorithm

			linearTree2.clear();
			if (y == 'r')
				traverse2(linearTree.get(i).right);
			else if (y == 'l')
				traverse2(linearTree.get(i).left);
			for (int j = 0; j < linearTree2.size() - 1; j++) {
				StringBuilder m = new StringBuilder(linearTree2.get(j).nodeCode);
				if (linearTree.get(i).nodeCode == null)
					swapsNumber = 0;
				else {
					swapsNumber = linearTree.get(i).nodeCode.length();
				}
				m.setCharAt(swapsNumber, x);
				linearTree2.get(j).nodeCode = m.toString();
			}
		}
	}

	void updateTree(char chr) {
		BTNode cur = root;
		if (!dic.contains(chr)) {
			dic.add(chr);

			if (dic.size() == 1) {
				int value = chr;
				finalCode.add(Integer.toBinaryString(value));

				cur.symbolCount = 1;
				cur.nodeSymbol = '-';

				cur.right = new BTNode();
				cur.right.nodeSymbol = chr;
				cur.right.nodeCode = "1";
				cur.right.nodeNumber = cur.nodeNumber - 1;
				cur.right.symbolCount = 1;

				cur.left = new BTNode();
				cur.left.nodeSymbol = '*';
				cur.left.nodeCode = "0";
				cur.left.nodeNumber = cur.nodeNumber - 2;
				cur.left.symbolCount = 0;
			}

			else {
				search(root);
				BTNode newNode = tmp;
				finalCode.add(newNode.nodeCode);
				int value = chr;
				finalCode.add(Integer.toBinaryString(value));

				newNode.right = new BTNode();
				newNode.right.nodeSymbol = chr;
				newNode.right.nodeCode = newNode.nodeCode + "1";
				newNode.right.nodeNumber = newNode.nodeNumber - 1;
				newNode.right.symbolCount = 1;

				newNode.symbolCount++;

				newNode.left = new BTNode();
				newNode.left.nodeSymbol = '*';
				newNode.left.nodeCode = newNode.nodeCode + "0";

				newNode.left.nodeNumber = newNode.nodeNumber - 2;
				newNode.left.symbolCount = 0;

				// inc the counter of all parents
				BTNode n = newNode;
				while (n != cur) {
					getParent(cur, n);
					n = parent;
					n.symbolCount++;
				}
				checkSwap('1', 'r');
			}
		} else {
			big.clear();
			search(cur, chr);
			finalCode.add(tmp.nodeCode);

			BTNode n = tmp;
			getParent(cur, n);
			n = parent;
			while (n != cur) {
				getParent(cur, parent);
				if (parent.right == n) {
					if (parent.left.symbolCount < tmp.symbolCount) {
						big.add(parent.left);
					}
				} else if (parent.left == n) {
					if (parent.right.symbolCount < tmp.symbolCount) {
						big.add(parent.right);
					}
				}
				n = parent;
			}
			BTNode max = new BTNode();
			if (big.size() != 0) {
				max = big.get(big.size() - 1);
			}

			if (big.size() != 0) {

				char xo = max.nodeSymbol;
				max.nodeSymbol = tmp.nodeSymbol;
				tmp.nodeSymbol = xo;

				int co = max.symbolCount;
				max.symbolCount = tmp.symbolCount;
				tmp.symbolCount = co;

				getParent(cur, max);
				BTNode b = parent;
				BTNode no = tmp;
				while (no != cur) {
					getParent(cur, no);
					if (parent == b) {
						break;
					}
					no = parent;
					no.symbolCount -= 1;
				}
			}
			checkSwap('1', 'r');
		}
		linearTree3.clear();
		traverse3(root);
	}

	public String compression(String input) {
		dic.clear();
		decompressed = "";
		linearTree.clear();
		linearTree2.clear();
		linearTree3.clear();
		finalCode.clear();
		big.clear();
		root = new BTNode();
		tmp = new BTNode();
		parent = new BTNode();
		for (int i = 0; i < input.length(); i++) {
			updateTree(input.charAt(i));
		}
		String finalResult = "";
		for (int i = 0; i < finalCode.size(); i++)
			finalResult += finalCode.get(i);
		return finalResult;
	}

	public void deCompression(String input) {
		dic.clear();
		decompressed = "";
		linearTree.clear();
		linearTree2.clear();
		linearTree3.clear();
		finalCode.clear();
		big.clear();
		root = new BTNode();
		tmp = new BTNode();
		parent = new BTNode();
		boolean flag2 = false;
		ArrayList<Character> deco = new ArrayList<Character>();
		for (int i = 0; i < input.length(); i++) {
			if (i == 0) {
				String x = "";
				for (int j = 0; j < 7; j++) {
					x += input.charAt(j);
				}
				i += 7;
				int num = Integer.parseInt(x, 2);
				flag2 = true;
				deco.add((char) num);
				decompressed += ((char) num);
				updateTree((char) num);
			} else {
				if (flag2 == true) {
					String x = "";
					for (int j = i; j < i + 7; j++) {
						x += input.charAt(j);
					}
					i += 6;
					int num = Integer.parseInt(x, 2);
					flag2 = true;
					char xo = (char) num;
					deco.add(xo);
					decompressed += xo;
					updateTree(xo);
					flag2 = false;
				} else {
					String x = Character.toString(input.charAt(i));
					for (int j = 0; j < linearTree3.size() - 1; j++) {
						if (linearTree3.get(j).nodeCode.equals(x)) {
							if (linearTree3.get(j).nodeSymbol != '*') {
								decompressed += linearTree3.get(j).nodeSymbol;
								updateTree(linearTree3.get(j).nodeSymbol);
								break;
							} else if (linearTree3.get(j).nodeSymbol == '*'
									&& linearTree3.get(j).right == null
									&& linearTree3.get(j).left == null) {
								flag2 = true;
								break;
							} else {
								i++;
								x += Character.toString(input.charAt(i));
								j = -1;
							}
						}
					}
				}
			}
			if (i == input.length() - 1) {
				return;
			}
		}
	}

	public String getDecompression() {
		return decompressed;
	}

	 public void printPostorder(BTNode node) {
	 if (node == null)
	 return;
	
	 printPostorder(node.left);
	
	 printPostorder(node.right);
	 System.out.println();
	 System.out.print(node.nodeNumber + " ");
	 System.out.print(node.nodeSymbol + " ");
	 System.out.print(node.nodeCode + " ");
	 System.out.print(node.symbolCount + " ");
	 System.out.println();
	 System.out.println();
	 System.out.println()	;
	 }

}

public class AdaptHoffman {
	public static void main(String args[]) throws FileNotFoundException {
		Scanner in = new Scanner(System.in);
		AdaptHoffmanOperations b = new AdaptHoffmanOperations();
		System.out
				.println("First compression : do you want to read from file and write into it or not");
		String answer = in.next();
		if (answer.equals("no")) {
			System.out.println("Enter the code you want to compressed : ");
			String input = in.next();
			try {
				for (int i = 0; i < input.length(); i++) {
					if (input.charAt(i) == '1' || input.charAt(i) == '2'
							|| input.charAt(i) == '3' || input.charAt(i) == '4'
							|| input.charAt(i) == '5' || input.charAt(i) == '6'
							|| input.charAt(i) == '7' || input.charAt(i) == '8'
							|| input.charAt(i) == '9') {
						Exception e = null;
						throw e;
					}
				}
				System.out.println(b.compression(input));

			} catch (Exception e) {
				System.out.println("enter valid characters");
			}

		} else if (answer.equals("yes")) {
			System.out
					.println("Enter the name of the file you want to read from : ");
			String fileName = in.next();
			File f = new File(fileName);
			if (f.isFile()) {
				Scanner inp = new Scanner(new File(fileName));
				String input = inp.next();
				try {
					for (int i = 0; i < input.length(); i++) {
						if (input.charAt(i) == '1' || input.charAt(i) == '2'
								|| input.charAt(i) == '3'
								|| input.charAt(i) == '4'
								|| input.charAt(i) == '5'
								|| input.charAt(i) == '6'
								|| input.charAt(i) == '7'
								|| input.charAt(i) == '8'
								|| input.charAt(i) == '9') {
							Exception e = null;
							throw e;

						}
					}
					inp.close();
					FileWriter out = new FileWriter(fileName);
					out.write(b.compression(input));
					out.close();
				} catch (Exception e) {
					System.out.println("not valid characters");
				}
			} else {
				System.out.println("the file doesn,t exist");
			}
		}

		System.out
				.println("second decompression : do you want to read from file and write into it or not");
		answer = in.next();
		if (answer.equals("no")) {
			try {

				System.out.println("Enter the code you want to decompressed : ");
				String ind = in.next();
				b.deCompression(ind);
				System.out.println(b.getDecompression());
			} catch (Exception e) {
				System.out.println("enter valid numbers");
			}
		} else if (answer.equals("yes")) {
			System.out
					.println("Enter the name of the file you want to read from : ");
			String fileName = in.next();
			File f = new File(fileName);
			if (f.isFile()) {
				Scanner inp = new Scanner(new File(fileName));
				String input = inp.next();
				try {
					b.deCompression(input);
					inp.close();
					FileWriter out = new FileWriter(fileName);
					out.write(b.getDecompression());
					out.close();
				} catch (Exception e) {
					System.out.println("file doesnt contain valid numbers");
				}
			}
		}
	}
}
