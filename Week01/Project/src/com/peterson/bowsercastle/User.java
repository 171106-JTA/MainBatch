package com.peterson.bowsercastle;

/**
 * User class that holds data for each user and methods to retrieve the data.
 * @author Alex Peterson
 */
public class User implements java.io.Serializable {
	private static final long serialVersionUID = 8191660665486147873L;
	private String name;
	private int hashedPassword, coins, stars;
	private Role role;
	
	/**
	 * Create a new user
	 * @param name of the new user
	 * @param hashedPassword of the user's password
	 * @param role of the user
	 */
	public User(final String name, final int hashedPassword, final Role role) {
		this.name = name;
		this.hashedPassword = hashedPassword;
		this.role = role;
	}
	
	/**
	 * @return how many coins the user has
	 */
	public int getCoins() {
		return coins;
	}

	/**
	 * Set the coins of the user
	 * @param coins new amount of coins
	 */
	public void setCoins(int coins) {
		this.coins = coins;
	}

	/**
	 * @return the name of the user
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the hashed password of the user
	 */
	public int getHashedPassword() {
		return hashedPassword;
	}

	/**
	 * @return the role of the user
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Set a new role for the user.
	 * @param role, new role for the user
	 */
	public void setRole(Role role) {
		this.role = role;
	}
	
	/**
	 * @return how many stars the user has
	 */
	public int getStars() {
		return stars;
	}
	
	public void setStars(int stars) {
		this.stars = stars;
	}

	public int levelUp() {
		return role.getValue();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + coins;
		result = prime * result + hashedPassword;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + stars;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (coins != other.coins)
			return false;
		if (hashedPassword != other.hashedPassword)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (role != other.role)
			return false;
		if (stars != other.stars)
			return false;
		return true;
	}
	
	
}