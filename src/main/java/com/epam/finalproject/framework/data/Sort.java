//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.epam.finalproject.framework.data;

import com.epam.finalproject.framework.util.StringUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


public class Sort implements Streamable<Sort.Order>, Serializable {
    public static final Direction DEFAULT_DIRECTION = Direction.ASC;

    private static final long serialVersionUID = 5737186511678863905L;
    private static final Sort UNSORTED = new Sort(List.of());
    private final List<Order> orders;

    protected Sort(List<Order> orders) {
        this.orders = orders;
    }

    private Sort(Direction direction, List<String> properties) {
        if (properties != null && !properties.isEmpty()) {
            this.orders = properties.stream().map(it -> new Order(direction, it)).collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("You have to provide at least one property to sort by!");
        }
    }

    public static Sort by(String... properties) {
        return properties.length == 0 ? unsorted() : new Sort(DEFAULT_DIRECTION, Arrays.asList(properties));
    }

    public static Sort by(List<Order> orders) {
        return orders.isEmpty() ? unsorted() : new Sort(orders);
    }

    public static Sort by(Order... orders) {
        return new Sort(Arrays.asList(orders));
    }

    public static Sort by(Direction direction, String... properties) {
        return by(Arrays.stream(properties).map(it -> new Order(direction, it)).collect(Collectors.toList()));
    }

    public static Sort unsorted() {
        return UNSORTED;
    }

    public Sort descending() {
        return this.withDirection(Direction.DESC);
    }

    public Sort ascending() {
        return this.withDirection(Direction.ASC);
    }

    public boolean isSorted() {
        return !this.isEmpty();
    }

    public boolean isEmpty() {
        return this.orders.isEmpty();
    }

    public boolean isUnsorted() {
        return !this.isSorted();
    }

    public Sort and(Sort sort) {
        ArrayList<Order> these = new ArrayList<>(this.toList());
        these.addAll(sort.orders);
        return by(these);
    }


    public Order getOrderFor(String property) {
        Iterator var2 = this.iterator();

        Order order;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            order = (Order) var2.next();
        } while (!order.getProperty().equals(property));

        return order;
    }

    public Iterator<Order> iterator() {
        return this.orders.iterator();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Sort)) {
            return false;
        } else {
            Sort that = (Sort) obj;
            return this.toList().equals(that.toList());
        }
    }

    public int hashCode() {
        int result = 17;
        result = 31 * result + this.orders.hashCode();
        return result;
    }

    public String toString() {
        return this.isEmpty() ? "UNSORTED" : this.orders.toString();
    }

    private Sort withDirection(Direction direction) {
        return by(this.stream().map(it -> it.with(direction)).collect(Collectors.toList()));
    }



    public enum Direction {
        ASC, DESC;

        Direction() {
        }

        public boolean isAscending() {
            return this.equals(ASC);
        }

        public boolean isDescending() {
            return this.equals(DESC);
        }
    }

    public static class Order implements Serializable {
        private static final long serialVersionUID = 1522511010900108987L;

        private final Direction direction;
        private final String property;
        private final boolean ignoreCase;


        public Order(Direction direction, String property) {
            this(direction, property, false);
        }

        private Order(Direction direction, String property, boolean ignoreCase) {
            if (!StringUtils.hasText(property)) {
                throw new IllegalArgumentException("Property must not be null or empty!");
            } else {
                this.direction = direction == null ? Sort.DEFAULT_DIRECTION : direction;
                this.property = property;
                this.ignoreCase = ignoreCase;
            }
        }

        public static Order by(String property) {
            return new Order(Sort.DEFAULT_DIRECTION, property);
        }

        public static Order asc(String property) {
            return new Order(Direction.ASC, property);
        }

        public static Order desc(String property) {
            return new Order(Direction.DESC, property);
        }

        public Direction getDirection() {
            return this.direction;
        }

        public String getProperty() {
            return this.property;
        }

        public boolean isAscending() {
            return this.direction.isAscending();
        }

        public boolean isDescending() {
            return this.direction.isDescending();
        }

        public boolean isIgnoreCase() {
            return this.ignoreCase;
        }

        public Order with(Direction direction) {
            return new Order(direction, this.property, this.ignoreCase);
        }

        public Order withProperty(String property) {
            return new Order(this.direction, property, this.ignoreCase);
        }

        public Sort withProperties(String... properties) {
            return Sort.by(this.direction, properties);
        }

        public Order ignoreCase() {
            return new Order(this.direction, this.property, true);
        }

        public int hashCode() {
            int result = 17;
            result = 31 * result + this.direction.hashCode();
            result = 31 * result + this.property.hashCode();
            result = 31 * result + (this.ignoreCase ? 1 : 0);
            return result;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            } else if (!(obj instanceof Order)) {
                return false;
            } else {
                Order that = (Order) obj;
                return this.direction.equals(that.direction) && this.property.equals(that.property) && this.ignoreCase == that.ignoreCase ;
            }
        }

        public String toString() {
            String result = String.format("%s: %s", this.property, this.direction);

            if (this.ignoreCase) {
                result = result + ", ignoring case";
            }

            return result;
        }
    }
}
