package com.alibaba.alink.common.linalg;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test cases for VectorUtil.
 */
public class VectorUtilTest {
	@Test
	public void testParseDenseAndToString() {
		DenseVector vec = new DenseVector(new double[]{1, 2, -3});
		String str = VectorUtil.toString(vec);
		Assert.assertEquals(str, "1.0 2.0 -3.0");
		Assert.assertArrayEquals(vec.getData(), VectorUtil.parseDense(str).getData(), 0);
	}

	@Test
	public void testParseDenseWithSpace() {
		DenseVector vec1 = VectorUtil.parseDense("1 2 -3");
		DenseVector vec2 = VectorUtil.parseDense(" 1  2  -3 ");
		DenseVector vec = new DenseVector(new double[]{1, 2, -3});
		Assert.assertArrayEquals(vec1.getData(), vec.getData(), 0);
		Assert.assertArrayEquals(vec2.getData(), vec.getData(), 0);
	}

	@Test
	public void testSparseToString() {
		SparseVector v1 = new SparseVector(8, new int[]{1, 3, 5, 7}, new double[]{2.0, 2.0, 2.0, 2.0});
		Assert.assertEquals(VectorUtil.toString(v1), "$8$1:2.0 3:2.0 5:2.0 7:2.0");
	}

	@Test
	public void testParseSparse() {
		SparseVector vec1 = VectorUtil.parseSparse("0:1 2:-3");
		SparseVector vec3 = VectorUtil.parseSparse("$4$0:1 2:-3");
		SparseVector vec4 = VectorUtil.parseSparse("$4$");
		SparseVector vec5 = VectorUtil.parseSparse("");
		Assert.assertEquals(vec1.get(0), 1., 0.);
		Assert.assertEquals(vec1.get(2), -3., 0.);
		Assert.assertArrayEquals(vec3.toDenseVector().getData(), new double[]{1, 0, -3, 0}, 0);
		Assert.assertEquals(vec3.size(), 4);
		Assert.assertArrayEquals(vec4.toDenseVector().getData(), new double[]{0, 0, 0, 0}, 0);
		Assert.assertEquals(vec4.size(), 4);
		Assert.assertEquals(vec5.size(), -1);
	}

	@Test
	public void testParseAndToStringOfVector() {
		Vector sparse = VectorUtil.parseSparse("0:1 2:-3");
		Vector dense = VectorUtil.parseDense("1 0 -3");

		Assert.assertEquals(VectorUtil.toString(sparse), "0:1.0 2:-3.0");
		Assert.assertEquals(VectorUtil.toString(dense), "1.0 0.0 -3.0");
		Assert.assertTrue(VectorUtil.getVector("$4$0:1 2:-3") instanceof SparseVector);
		Assert.assertTrue(VectorUtil.getVector("1 0 -3") instanceof DenseVector);
	}
}