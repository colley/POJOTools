<service-builder package-path="${entity.packagePath}">
<#if entity.listEntity??>
<#list entity.listEntity as column>
	<entity name="${column.name}"   <#if column.superClazz??>superclass="${column.superClazz}"</#if> >
		<#if column.propertyList??>
			<#list column.propertyList as property>
		<property  name="${property.name}"   type="${property.type}"  <#if property.entity??>entity="${property.entity}"</#if> />
			</#list>
		</#if>
	</entity>
</#list>
</#if>
</service-builder>