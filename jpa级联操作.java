# Spring-Data-Jpa

spring data jpa 中@OneToMany在one的一方级联保存中，附表信息可添加，但关联外键不能被赋值。

@OneToMany和@ManyToOne
  实体类User
  @Entity
  public class User {
      private String username;
      private String password;
      private Integer sex;
      
      @ManyToOne
      @JoinColumn(name = "organization_id")
      private Organization organization; //部门
      
  }
  
  实体类Organization
  @Entity
  public class Organization{
      private String name; //名称
      private String type; //类型
      
      @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
      private Set<User> users = new HashSet<>();
  }
  
  mappedBy：避免生成中间表
  问题1：关联保存
  spring data jpa 中@OneToMany在one的一方级联保存中，附表信息可添加，但关联外键不能被赋值。
  即在Organization实体中set进users，添加信息时，User实体中的organization为null。
  
  问题2：关联查询
  在one的一方要将many一方转为JSON时，需在many一方的关联属性的set方法上写上注解@JsonBackReference
  @Entity
  public class User {
      private String username;
      private String password;
      private Integer sex;
      
      @ManyToOne
      @JoinColumn(name = "organization_id")
      private Organization organization; //部门
      
      public Organization getOrganization() {
        return organization;
      }

      @JsonBackReference
      public void setOrganization(Organization organization) {
          this.organization = organization;
      }
      
  }
