# DESAFIO AZUL SEGUROS

## INSTALAÇÃO

### CONFIGURAR DATASOURCE NO WILDFLY
1. Iniciar o servidor wildfly
1. Abrir o arquivo configurations/standalone e adicionar no subsessão datasources a seguinte configuração
   
<pre><code>
   &lt;datasource jta="true" 
        jndi-name="java:jboss/datasources/azul-seguros/desafioDS" 
        pool-name="desafio" enabled="true" use-java-context="
        true" use-ccm="true"&gt;
        &lt;connection-url&gt;jdbc:mysql://localhost:3306/desafio&lt;/connection-url&gt;
        &lt;driver&gt;mysql&lt;/driver&gt;
        &lt;transaction-isolation&gt;TRANSACTION_READ_COMMITTED&lt;/transaction-isolation&gt;
            &lt;security&gt;
                &lt;user-name&gt;** inserir aqui o usuario do banco **&lt;/user-name&gt;
                &lt;password&gt;** inserir aqui a senha do usuario **&lt;/password&gt;
            &lt;/security&gt;    
   &lt;/datasource&gt;
</code></pre>

{:start="3"}
3. Na sessão drivers inserir o trecho abaixo 
<pre><code>

&lt;driver name="mysql" module="com.mysql"&gt;
    &lt;driver-class&gt;com.mysql.cj.jdbc.Driverlt;/driver-class&gt;
    &lt;xa-datasource-class&gt;com.mysql.cj.jdbc.MysqlXADataSourcelt;/xa-datasource-class&gt;
&lt;/driver&gt;

</code></pre>
{:start="4"}
4. Adicionar no caminho modules/system/layers/base/com a pasta mysql
4. Na pasta modules/system/layers/base/com/mysql adicionar  a pasta main
5. Na pasta modules/system/layers/base/com/mysql/main adicionar o arquivo mysql-connector-java-8.0.22.jar
6. Na pasta Na pasta modules/system/layers/base/com/mysql/main adicionar o arquivo module.xml com o conteudo

<pre><code>
&lt;module xmlns="urn:jboss:module:1.5" name="com.mysql"&gt;
    &lt;resources&gt;
        &lt;resource-root path="mysql-connector-java-8.0.22.jar" /&gt;
    &lt;/resources&gt;
    &lt;dependencies&gt;
        &lt;module name="javax.api"/&gt;
        &lt;module name="javax.transaction.api"/&gt;
    &lt;/dependencies&gt;
&lt;/module>

</code></pre>

{:start="7"}
7. executar mvn clean package
7. copiar o arquivo target/azul-seguros-1.0-SNAPSHOT.war para dentro de standalone/deployment
7. subir o servidor
