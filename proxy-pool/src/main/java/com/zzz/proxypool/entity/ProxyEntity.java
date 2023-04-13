package com.zzz.proxypool.entity;


import java.net.Proxy;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @description: Ip代理实体
 * @author: zzz
 * @since: 2023/4/1 18:29
 **/
public class ProxyEntity {
    private Integer id;

    private String host;

    private Integer port;

    private Boolean available;

    private String type = "http";

    private String country;

    private String location;

    /**
     * 匿名类型
     */
    private String anonymityType;

    private Long responseTime;

    private LocalDateTime validateTime;

    private String source;

    private Proxy.Type proxyType;

    public Integer getId() {
        return id;
    }

    public ProxyEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getHost() {
        return host;
    }

    public ProxyEntity setHost(String host) {
        this.host = host;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public ProxyEntity setPort(Integer port) {
        this.port = port;
        return this;
    }

    public Boolean getAvailable() {
        return available;
    }

    public ProxyEntity setAvailable(Boolean available) {
        this.available = available;
        return this;
    }

    public String getType() {
        return type;
    }

    public ProxyEntity setType(String type) {
        this.type = type;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public ProxyEntity setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public ProxyEntity setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getAnonymityType() {
        return anonymityType;
    }

    public ProxyEntity setAnonymityType(String anonymityType) {
        this.anonymityType = anonymityType;
        return this;
    }

    public Long getResponseTime() {
        return responseTime;
    }

    public ProxyEntity setResponseTime(Long responseTime) {
        this.responseTime = responseTime;
        return this;
    }

    public LocalDateTime getValidateTime() {
        return validateTime;
    }

    public ProxyEntity setValidateTime(LocalDateTime validateTime) {
        this.validateTime = validateTime;
        return this;
    }

    public String getSource() {
        return source;
    }

    public ProxyEntity setSource(String source) {
        this.source = source;
        return this;
    }

    public Proxy.Type getProxyType() {
        return proxyType;
    }

    public ProxyEntity setProxyType(Proxy.Type proxyType) {
        this.proxyType = proxyType;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProxyEntity that = (ProxyEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(host, that.host) && Objects.equals(port, that.port) && Objects.equals(available, that.available) && Objects.equals(type, that.type) && Objects.equals(country, that.country) && Objects.equals(location, that.location) && Objects.equals(anonymityType, that.anonymityType) && Objects.equals(responseTime, that.responseTime) && Objects.equals(validateTime, that.validateTime) && Objects.equals(source, that.source) && proxyType == that.proxyType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, host, port, available, type, country, location, anonymityType, responseTime, validateTime, source, proxyType);
    }

    @Override
    public String toString() {
        return "ProxyEntity{" +
                "id=" + id +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", available=" + available +
                ", type='" + type + '\'' +
                ", country='" + country + '\'' +
                ", location='" + location + '\'' +
                ", anonymityType='" + anonymityType + '\'' +
                ", responseTime=" + responseTime +
                ", validateTime=" + validateTime +
                ", source='" + source + '\'' +
                ", proxyType=" + proxyType +
                '}';
    }
}
