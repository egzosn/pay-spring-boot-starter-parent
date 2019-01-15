package com.egzosn.pay.spring.boot.common.config;

public abstract class PayConfigurerAdapter<O, B extends PayBuilder<O>> implements PayConfigurer<O, B> {
	private B payBuilder;


	@Override
	public void init(B builder) throws Exception {
	}

	@Override
	public void configure(B builder) throws Exception {
	}

	/**
	 * Return the {@link PayBuilder} when done using the {@link PayConfigurer}.
	 * This is useful for method chaining.
	 *
	 * @return
	 *
	 */
	public B and() {
		return getBuilder();
	}

	/**
	 * Gets the {@link PayBuilder}. Cannot be null.
	 *
	 * @return the {@link PayBuilder}
	 * @throws IllegalStateException if {@link PayBuilder} is null
	 */
	protected final B getBuilder() {
		if (payBuilder == null) {
			throw new IllegalStateException("payBuilder cannot be null");
		}
		return payBuilder;
	}


	/**
	 * Sets the {@link PayBuilder} to be used. This is automatically set when using
	 * {@link AbstractConfiguredPayBuilder#apply(PayConfigurerAdapter)}
	 *
	 * @param builder the {@link PayBuilder} to set
	 */
	public void setBuilder(B builder) {
		this.payBuilder = builder;
	}

}