package org.aprestos.labs.challenges;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ArraysAndStrings {

	@Test
	public void test() throws Exception {

		Assert.assertEquals(true, doesStringHasOnlyUniqueChars_1_1("asdfghjklpouytrewq"));
		Assert.assertEquals(false, doesStringHasOnlyUniqueChars_1_1("asdfghjklpouytrewqa"));

		Assert.assertEquals("aqwertyuoplkjhgfdsa", reverseString_1_2("asdfghjklpouytrewqa"));

		Assert.assertEquals("asdfghjklpouytrewq", removeDuplicatesInString_1_3("asdfgghjklpouuytrewqa"));
		Assert.assertEquals("asdfghjklpouytrewq", removeDuplicatesInString2_1_3("asdfgghjklpouuytrewqa"));

		Assert.assertEquals(true, isAnagram_1_4("asdfgghjklpouuytrewqa", "asdwfghjklpouuytreqag"));
		Assert.assertEquals(false, isAnagram_1_4("asdfgghjklpouuytrewqa", "asdwfghjklpouuytreqagW"));

		Assert.assertEquals("%20sdfgghjklpouuytrewq%20", replace_1_5("asdfgghjklpouuytrewqa", "a", "%20"));
		Assert.assertEquals("lsdfgghjklp%20%20%20ytrewql", replace_1_5("lsdfgghjklpaaaytrewql", "a", "%20"));

		Assert.assertArrayEquals(new int[][] { { 4, 7, 1, 5 }, { 3, 6, 0, 4 }, { 2, 5, 9, 3 }, { 1, 4, 8, 2 } },
				rotateMatrix_1_6(new int[][] { { 1, 2, 3, 4 }, { 4, 5, 6, 7 }, { 8, 9, 0, 1 }, { 2, 3, 4, 5 } }));
		Assert.assertArrayEquals(new int[][] { { 3, 6, 9 }, { 2, 5, 8 }, { 1, 4, 7 } },
				rotateMatrix_1_6(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } }));

		Assert.assertArrayEquals(new int[][] { { 1, 0, 3 }, { 0, 0, 0 }, { 7, 0, 9 } },
				zeroMatrixRowAndColumn_1_7(new int[][] { { 1, 2, 3 }, { 4, 0, 6 }, { 7, 8, 9 } }));

		Assert.assertArrayEquals(new int[][] { { 1, 2, 0, 4 }, { 4, 1, 0, 4 }, { 3, 3, 0, 2 }, { 0, 0, 0, 0 } },
				zeroMatrixRowAndColumn_1_7(
						new int[][] { { 1, 2, 3, 4 }, { 4, 1, 6, 4 }, { 3, 3, 3, 2 }, { 7, 8, 0, 2 } }));

		Assert.assertEquals(true, isSubstring_1_8("waterbottle", "erbottlewat"));
		Assert.assertEquals(false, isSubstring_1_8("waterbottle", "erbottlewadt"));

		Assert.assertArrayEquals(new int[] { 5, 1, 2, 3, 4 }, leftRotateArray(new int[] { 1, 2, 3, 4, 5 }, 4));
		Assert.assertArrayEquals(new int[] { 5, 6, 1, 2, 3, 4 }, leftRotateArray(new int[] { 1, 2, 3, 4, 5, 6 }, 4));
		Assert.assertArrayEquals(new int[] { 58, 26, 10, 86, 51, 41, 73, 89, 7, 10 },
				leftRotateArray(new int[] { 41, 73, 89, 7, 10, 58, 26, 10, 86, 51 }, 5));

		Assert.assertArrayEquals(
				new int[] { 77, 97, 58, 1, 86, 58, 26, 10, 86, 51, 41, 73, 89, 7, 10, 1, 59, 58, 84, 77 },
				leftRotateArray(
						new int[] { 41, 73, 89, 7, 10, 1, 59, 58, 84, 77, 77, 97, 58, 1, 86, 58, 26, 10, 86, 51 }, 10));

		Assert.assertEquals(3, newYearChaos(new int[] { 2, 1, 5, 3, 4 }));
		Assert.assertEquals(-1, newYearChaos(new int[] { 2, 5, 1, 3, 4 }));
		Assert.assertEquals(7, newYearChaos(new int[] { 1, 2, 5, 3, 7, 8, 6, 4 }));

		String input = "2 1 5 6 3 4 9 8 11 7 10 14 13 12 17 16 15 19 18 22 20 24 23 21 27 28 25 26 30 29 33 32 31 35 36 34 39 38 37 42 40 44 41 43 47 46 48 45 50 52 49 51 54 56 55 53 59 58 57 61 63 60 65 64 67 68 62 69 66 72 70 74 73 71 77 75 79 78 81 82 80 76 85 84 83 86 89 90 88 87 92 91 95 94 93 98 97 100 96 102 99 104 101 105 103 108 106 109 107 112 111 110 113 116 114 118 119 117 115 122 121 120 124 123 127 125 126 130 129 128 131 133 135 136 132 134 139 140 138 137 143 141 144 146 145 142 148 150 147 149 153 152 155 151 157 154 158 159 156 161 160 164 165 163 167 166 162 170 171 172 168 169 175 173 174 177 176 180 181 178 179 183 182 184 187 188 185 190 189 186 191 194 192 196 197 195 199 193 198 202 200 204 205 203 207 206 201 210 209 211 208 214 215 216 212 218 217 220 213 222 219 224 221 223 227 226 225 230 231 229 228 234 235 233 237 232 239 236 241 238 240 243 242 246 245 248 249 250 247 244 253 252 251 256 255 258 254 257 259 261 262 263 265 264 260 268 266 267 271 270 273 269 274 272 275 278 276 279 277 282 283 280 281 286 284 288 287 290 289 285 293 291 292 296 294 298 297 299 295 302 301 304 303 306 300 305 309 308 307 312 311 314 315 313 310 316 319 318 321 320 317 324 325 322 323 328 327 330 326 332 331 329 335 334 333 336 338 337 341 340 339 344 343 342 347 345 349 346 351 350 348 353 355 352 357 358 354 356 359 361 360 364 362 366 365 363 368 370 367 371 372 369 374 373 376 375 378 379 377 382 381 383 380 386 387 384 385 390 388 392 391 389 393 396 397 394 398 395 401 400 403 402 399 405 407 406 409 408 411 410 404 413 412 415 417 416 414 420 419 422 421 418 424 426 423 425 428 427 431 430 429 434 435 436 437 432 433 440 438 439 443 441 445 442 447 444 448 446 449 452 451 450 455 453 454 457 456 460 459 458 463 462 464 461 467 465 466 470 469 472 468 474 471 475 473 477 476 480 479 478 483 482 485 481 487 484 489 490 491 488 492 486 494 495 496 498 493 500 499 497 502 504 501 503 507 506 505 509 511 508 513 510 512 514 516 518 519 515 521 522 520 524 517 523 525 526 529 527 531 528 533 532 534 530 537 536 539 535 541 538 540 543 544 542 547 548 545 549 546 552 550 551 554 553 557 555 556 560 559 558 563 562 564 561 567 568 566 565 569 572 571 570 575 574 577 576 579 573 580 578 583 581 584 582 587 586 585 590 589 588 593 594 592 595 591 598 599 596 597 602 603 604 605 600 601 608 609 607 611 612 606 610 615 616 614 613 619 618 617 622 620 624 621 626 625 623 628 627 631 630 633 629 635 632 637 636 634 638 640 642 639 641 645 644 647 643 646 650 648 652 653 654 649 651 656 658 657 655 661 659 660 663 664 666 662 668 667 670 665 671 673 669 672 676 677 674 679 675 680 678 681 684 682 686 685 683 689 690 688 687 693 692 691 696 695 698 694 700 701 702 697 704 699 706 703 705 709 707 711 712 710 708 713 716 715 714 718 720 721 719 723 717 722 726 725 724 729 728 727 730 733 732 735 734 736 731 738 737 741 739 740 744 743 742 747 746 745 750 748 752 749 753 751 756 754 758 755 757 761 760 759 764 763 762 767 765 768 766 771 770 769 774 773 776 772 778 777 779 775 781 780 783 784 782 786 788 789 787 790 785 793 791 792 796 795 794 798 797 801 799 803 800 805 802 804 808 806 807 811 809 810 814 812 813 817 816 819 818 815 820 821 823 822 824 826 827 825 828 831 829 830 834 833 836 832 837 839 838 841 835 840 844 842 846 845 843 849 847 851 850 852 848 855 854 853 857 856 858 861 862 860 859 863 866 865 864 867 870 869 868 872 874 875 871 873 877 878 876 880 881 879 884 883 885 882 888 886 890 891 889 893 887 895 892 896 898 894 899 897 902 901 903 905 900 904 908 907 910 909 906 912 911 915 913 916 918 914 919 921 917 923 920 924 922 927 925 929 928 926 932 931 934 930 933 935 937 939 940 938 936 943 944 942 941 947 946 948 945 951 950 949 953 952 956 954 958 957 955 961 962 963 959 964 966 960 965 969 968 971 967 970 974 972 976 973 975 979 977 981 982 978 980 983 986 984 985 989 988 987 990 993 991 995 994 997 992 999 1000 996 998";
		Assert.assertEquals(966, newYearChaos(strToIntArray(input)));

		input = "1 2 5 3 4 7 8 6";
		Assert.assertEquals(4, newYearChaos(strToIntArray(input)));

		input = "1 2 5 3 4 7 8 6";

		Assert.assertEquals(4, newYearChaos2(strToIntArray(input)));

		input = "1 2 5 3 4 7 8 6";
		Assert.assertArrayEquals(strToIntArray("1 2 3 4 5 6 7 8"), sortIt(strToIntArray(input)));

		Assert.assertArrayEquals(strToIntArray("1 2 3 4 5 6 7 8"), sortIt(strToIntArray("4 7 3 6 2 8 1 5")));
		Assert.assertArrayEquals(strToIntArray("0 1 5 5 8 8"), sortIt(strToIntArray("1 0 8 8 5 5")));
		Assert.assertArrayEquals(strToIntArray("0 0 0"), sortIt(strToIntArray("0 0 0")));
		Assert.assertArrayEquals(strToIntArray("1 2 3 4 5 6 7 8"), sortIt(strToIntArray("6 8 7 1 2 3 4 5")));

		// Assert.assertEquals(3, sortSteps(strToIntArray("4 3 1 2")));

		Assert.assertEquals(3, minSwapsToSort(strToIntArray("4 3 1 2")));
		Assert.assertEquals(3, minSwapsToSort(strToIntArray("2 3 4 1 5")));
		Assert.assertEquals(3, minSwapsToSort(strToIntArray("1 3 5 2 4 6 8")));

		Assert.assertArrayEquals(
				new int[][] { { 1, 3 }, { 2, 4 }, { 3, 5 }, { 4, 6 }, { 5, 7 }, { 6, 0 }, { 7, 2 }, { 8, 1 } },
				sortItAndKeepInitialIndexes(strToIntArray("6 8 7 1 2 3 4 5")));

		Assert.assertEquals(46, minSwapsToSort(strToIntArray(
				"2 31 1 38 29 5 44 6 12 18 39 9 48 49 13 11 7 27 14 33 50 21 46 23 15 26 8 47 40 3 32 22 34 42 16 41 24 10 4 28 36 30 37 35 20 17 45 43 25 19")));

	}

	private int minSwapsToSort(int[] arr) {

		int steps = 0;
		int[][] r = sortItAndKeepInitialIndexes(arr);

		int[] oldIndexMap = new int[arr.length];
		for (int i = 0; i < oldIndexMap.length; i++)
			oldIndexMap[i] = i;

		for (int i = 0; i < r.length; i++) {
			int val = r[i][0];
			int ipos = r[i][1];

			if (arr[i] != val) {

				swap1(arr, oldIndexMap[ipos], i);
				oldIndexMap[i] = oldIndexMap[ipos];
				// oldIndexMap[ipos] = i;

				steps++;
			}
		}

		return steps;
	}

	private int[] sortIt(int[] a) {

		sort(a, 0, a.length - 1);
		return a;

	}

	private int[][] sortItAndKeepInitialIndexes(int[] a) {

		int[][] r = new int[a.length][];

		for (int i = 0; i < a.length; i++) {
			r[i] = new int[] { a[i], i };
		}

		sortAndKeepScore(r, 0, a.length - 1);
		return r;

	}

	private void sortAndKeepScore(int[][] a, int lo, int hi) {

		if (lo >= hi)
			return;

		int v = a[lo][0];
		int l = lo, i = lo;
		int h = hi;

		while (h >= i) {

			if (a[i][0] < v) {
				swap2(a, i, l);
				i++;
				l++;
			} else if (a[i][0] > v) {
				swap2(a, i, h);
				h--;
			} else
				i++;
		}

		sortAndKeepScore(a, lo, l - 1);
		sortAndKeepScore(a, h + 1, hi);

	}

	private void swap1(int[] a, int i, int j) {
		int v = a[i];
		a[i] = a[j];
		a[j] = v;
	}

	private void swap2(int[][] a, int i, int j) {
		int[] v = a[i];
		a[i] = a[j];
		a[j] = v;
	}

	private void sort(int[] a, int lo, int hi) {

		if (lo >= hi)
			return;

		int v = a[lo];
		int l = lo, i = lo;
		int h = hi;

		while (h >= i) {

			if (a[i] < v) {
				int o = a[i];
				a[i] = a[l];
				a[l] = o;
				i++;
				l++;
			} else if (a[i] > v) {
				int o = a[h];
				a[h] = a[i];
				a[i] = o;
				h--;
			} else
				i++;
		}

		sort(a, lo, l - 1);
		sort(a, h + 1, hi);

	}

	private int[] swap(int[] a, int i, int j) {
		int v = a[i];
		a[i] = a[j];
		a[j] = v;
		return a;
	}

	private int sortSteps(int[] a) {
		return sortnow(a, 0, a.length - 1);
	}

	private int sortnow(int[] a, int lo, int hi) {

		int steps = 0;

		if (hi <= lo)
			return steps;

		int v = a[((hi - lo) / 2) + lo];
		int l = lo, h = hi, i = lo;

		while (i <= h) {
			if (a[i] < v) {
				swap(a, i++, l++);
				steps++;
			} else if (a[i] > v) {
				swap(a, i, h--);
				steps++;
			} else
				i++;
		}

		steps += sortnow(a, lo, l - 1);
		steps += sortnow(a, h + 1, hi);

		return steps;
	}

	private int[] strToIntArray(String s) {

		String[] _s = s.split(" ");
		int[] r = new int[_s.length];
		int index = 0;
		for (String o : _s)
			r[index++] = Integer.parseInt(o.trim());

		return r;
	}

	private int minimumSwaps(int[] a) {
		int r = 0;

		int val;

		return r;
	}

	/*
	 * https://www.hackerrank.com/challenges/new-year-chaos/problem?h_l=interview&
	 * playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=arrays&
	 * h_r=next-challenge&h_v=zen
	 */

	private int newYearChaos2(int[] q) {

		int bribe = 0;
		boolean chaotic = false;
		for (int i = 0; i < q.length; i++) {
			if (q[i] > (i + 3)) {
				chaotic = true;
				break;
			}
			// everything 2 slots before your initial position
			int m = Math.max(0, q[i] - 2);
			for (int j = m; j < i; j++)
				if (q[j] > q[i])// if bigger than you then there was a bribe
					bribe++;
		}
		if (chaotic)
			return -1;
		else
			return bribe;
	}

	private int newYearChaos(int[] q) {
		int r = 0;

		for (int i = 0; i < q.length; i++) {

			if (q[i] > (i + 3)) {
				r = -1;
				break;
			}
			int value = q[i];

			int initialIndex = q[i] - 1;
			// this has been bribed
			int bribersLimitIndex = Math.max(0, initialIndex - 1);
			for (int j = bribersLimitIndex; j < i; j++) {
				if (q[j] > q[i])
					r++;
			}

		}

		return r;
	}

	private int[] leftRotateArray(int[] a, int n) {

		if (n == a.length)
			return a;

		int r[] = new int[a.length];
		for (int i = 0; i < a.length; i++) {
			int newIndex = (a.length - 1) - ((a.length - 1 - i + n) % a.length);
			r[newIndex] = a[i];
		}

		return r;
	}

	private boolean isSubstring_1_8(String s1, String s2) {
		boolean r = false;

		char[] _s1 = s1.toCharArray();
		char[] _s2 = s2.toCharArray();
		int j = 0, i;
		boolean foundStart = false;

		for (i = 0; i < _s1.length; i++)
			if (_s1[i] == _s2[j]) {
				foundStart = true;
				break;
			}

		if (foundStart) {
			int matches = 0;
			while (_s1[i++] == _s2[j++]) {
				matches++;
				if (matches == _s2.length) {
					r = true;
					break;
				}

				if (j == _s2.length)
					break;
				if (i == _s1.length)
					i = 0;

			}
		}

		return r;
	}

	private int[][] zeroMatrixRowAndColumn_1_7(int[][] matrix) {

		int[] cols = new int[matrix.length], rows = new int[matrix.length];
		int numFields = matrix.length * matrix.length;

		for (int i = 0; i < numFields; i++) {
			int row = i / matrix.length;
			int col = i % matrix.length;
			if (0 == matrix[row][col]) {
				cols[col] = 1;
				rows[row] = 1;
			}
		}
		for (int index = 0; index < rows.length; index++) {
			if (1 == rows[index])
				for (int c = 0; c < matrix.length; c++)
					matrix[index][c] = 0;
		}
		for (int index = 0; index < cols.length; index++) {
			if (1 == cols[index])
				for (int r = 0; r < matrix.length; r++)
					matrix[r][index] = 0;
		}

		return matrix;
	}

	private int[][] rotateMatrix_1_6(int[][] matrix) {

		for (int layer = 0; layer < matrix.length / 2; layer++) {

			int layerHigherIndex = matrix.length - 1 - layer;
			int layerLowerIndex = layer;

			for (int i = 0; i < (layerHigherIndex - layerLowerIndex); i++) {
				int n = matrix[layerLowerIndex][layerLowerIndex + i];
				int w = matrix[layerHigherIndex - i][layerLowerIndex];
				int s = matrix[layerHigherIndex][layerHigherIndex - i];
				int e = matrix[layerLowerIndex + i][layerHigherIndex];

				matrix[layerHigherIndex - i][layerLowerIndex] = n;
				matrix[layerHigherIndex][layerHigherIndex - i] = w;
				matrix[layerLowerIndex + i][layerHigherIndex] = s;
				matrix[layerLowerIndex][layerLowerIndex + i] = e;
			}
		}

		return matrix;
	}

	private String replace_1_5(String s, String o, String n) {

		char[] r = s.toCharArray();
		int matchLength = o.length();
		int j = 0;

		char[] _o = o.toCharArray();
		char[] _n = n.toCharArray();

		for (int i = 0; i < r.length; i++) {
			if (r[i] == _o[j]) {
				j++;
			} else
				j = 0;

			if (matchLength == j) {
				int sizeIncrement = _n.length - _o.length;
				char[] _r = new char[r.length + sizeIncrement];
				int includePoint = i - j + 1;
				System.arraycopy(r, 0, _r, 0, includePoint);
				System.arraycopy(_n, 0, _r, includePoint, _n.length);
				System.arraycopy(r, i + 1, _r, includePoint + _n.length, r.length - i - 1);
				r = _r;
				j = 0;
			}

		}
		return new String(r);
	}

	private char[] include(char[] s, char[] i, int index) {
		if (null == s || null == i)
			throw new IllegalArgumentException("can't be null");
		if (0 == i.length)
			return s;
		if (0 == s.length)
			return i;

		char[] r = new char[s.length + i.length];
		for (int j = 0; j < index; j++) {
			r[j] = s[j];
		}
		for (int j = 0; j < i.length; j++) {
			r[index + j] = i[j];
		}
		for (int j = index; j < s.length; j++) {
			r[j + i.length] = s[j];
		}

		return r;
	}

	private boolean isAnagram_1_4(String s1, String s2) {

		int[] map1 = new int[256];
		int[] map2 = new int[256];

		for (char c : s1.toCharArray())
			map1[c]++;
		for (char c : s2.toCharArray())
			map2[c]++;

		for (int i = 0; i < 256; i++)
			if (map1[i] != map2[i])
				return false;

		return true;
	}

	private String removeDuplicatesInString2_1_3(String s) {

		if (null == s || 2 > s.length())
			return s;
		char[] _s = s.toCharArray();

		int tail = 1;
		for (int i = 1; i < _s.length; i++) {

			int j;
			for (j = 0; j < tail; j++)
				if (_s[j] == _s[i])
					break; // there was a duplicate

			if (j == tail) {
				// there were no duplicates
				// move the tail
				_s[tail] = _s[i];
				++tail;
			}

		}

		for (int i = tail; i < _s.length; i++)
			_s[i] = 0;

		return new String(_s).trim();
	}

	private String removeDuplicatesInString_1_3(String s) {

		int[] scores = new int[256];
		char[] _s = s.toCharArray();

		for (int i = 0; i < _s.length; i++) {
			char c = _s[i];
			if (0 != c) {
				if (0 == scores[c])
					scores[c] = 1;
				else {
					for (int destinIndex = i, sourceIndex = i
							+ 1; destinIndex < _s.length; destinIndex++, sourceIndex++)
						_s[destinIndex] = (_s.length == sourceIndex ? 0 : _s[sourceIndex]);
				}
			}

		}

		return new String(_s).trim();
	}

	private String reverseString_1_2(String s) {

		char[] a = s.toCharArray();
		char[] r = new char[a.length];

		for (int i = a.length - 1, j = 0; i >= 0; i--, j++)
			r[j] = a[i];

		return new String(r);
	}

	private boolean doesStringHasOnlyUniqueChars_1_1(String s) {
		boolean r = true;

		// int[] scores = new int[Character.MAX_CODE_POINT-Character.MIN_CODE_POINT+1];
		int[] scores = new int[256];

		for (char c : s.toCharArray()) {
			if (0 == scores[c])
				scores[c] = 1;
			else
				return false;
		}

		return r;
	}

}
