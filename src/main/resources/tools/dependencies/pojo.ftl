
package ${packagePath};

<#if entity.superClazz??>
<#else>
import java.io.Serializable;
</#if>

import org.apache.commons.lang.builder.ToStringBuilder;

<#if entity.imports??>
	<#list entity.imports as _importStr>
import 	${_importStr};
	</#list>
</#if>

@SuppressWarnings("serial")
public class ${entity.name?cap_first} <#if entity.superClazz??> extends <#if (entity.superClazz.packagePath??)>${entity.superClazz.packagePath}.</#if>${entity.superClazz.clazzName} </#if> <#if entity.superClazz??> <#else> implements Serializable</#if>{
<#if entity.baseList??>
<#list entity.baseList as column>
	<#if column.desc??>/**${column.desc}*/</#if>
	private ${column.type} ${column.name};
	${StringPool.RETURN_NEW_LINE}
</#list>
</#if>

<#if entity.pojoList??>
<#list entity.pojoList as column>
	<#if column.desc??>/**${column.desc}*/</#if>
	private ${column.type} ${column.name};
	${StringPool.RETURN_NEW_LINE}
</#list>
</#if>

<#if entity.collectionList??>
<#list entity.collectionList as column>
	<#if column.desc??>/**${column.desc}*/</#if>
	private ${column.type}<${column.ejbName}> ${column.name};
	${StringPool.RETURN_NEW_LINE}
</#list>
</#if>

<#if entity.baseList??>
<#list entity.baseList as column>
	public ${column.type} get${column.name?cap_first}() {
		return ${column.name};
	}

	public void set${column.name?cap_first}(${column.type} ${autoAnalyzer.analyzerPropName(column.name)}) {
		this.${column.name} = ${autoAnalyzer.analyzerPropName(column.name)};
	}
	
	public void set${column.name?cap_first}(${column.type} ${autoAnalyzer.analyzerPropName(column.name)},${column.type} defaultVal) {
		if(${autoAnalyzer.analyzerPropName(column.name)}==null) ${autoAnalyzer.analyzerPropName(column.name)} = defaultVal;
		this.${column.name} = ${autoAnalyzer.analyzerPropName(column.name)};
	}
</#list>
</#if>

<#if entity.pojoList??>
<#list entity.pojoList as column>
	public ${column.type} get${column.name?cap_first}() {
		return ${column.name};
	}

	public void set${column.name?cap_first}(${column.type} ${autoAnalyzer.analyzerPropName(column.name)}) {
		this.${column.name} = ${autoAnalyzer.analyzerPropName(column.name)};
	}
</#list>
</#if>

<#if entity.collectionList??>
<#list entity.collectionList as column>
	public ${column.type}<${column.ejbName}> get${column.name?cap_first}() {
	<#if column.type=='List'>
		if(this.${column.name} == null){
			this.${column.name} = new ArrayList<${column.ejbName}>();
		}
	</#if>
		return ${column.name};
	}

	public void set${column.name?cap_first}(${column.type}<${column.ejbName}> ${autoAnalyzer.analyzerPropName(column.name)}) {
		this.${column.name} = ${autoAnalyzer.analyzerPropName(column.name)};
	}
</#list>
</#if>

  public static class PojoBuilder {
       private ${entity.name?cap_first} pojoEntity = null;
        
        public PojoBuilder(){
        	pojoEntity = new ${entity.name?cap_first}();
        }
	<#if entity.baseList??>
	<#list entity.baseList as column>
	
		public PojoBuilder set${column.name?cap_first}(${column.type} ${autoAnalyzer.analyzerPropName(column.name)}) {
			pojoEntity.set${column.name?cap_first}(${autoAnalyzer.analyzerPropName(column.name)});
			return this;
		}
	</#list>
	</#if>
	
	<#if entity.pojoList??>
	<#list entity.pojoList as column>
		public PojoBuilder set${column.name?cap_first}(${column.type} ${autoAnalyzer.analyzerPropName(column.name)}) {
			pojoEntity.set${column.name?cap_first}(${autoAnalyzer.analyzerPropName(column.name)});
			return this;
		}
	</#list>
	</#if>
	
	<#if entity.collectionList??>
	<#list entity.collectionList as column>
		public PojoBuilder set${column.name?cap_first}(${column.type}<${column.ejbName}> ${autoAnalyzer.analyzerPropName(column.name)}) {
			pojoEntity.set${column.name?cap_first}(${autoAnalyzer.analyzerPropName(column.name)});
			return this;
		}
	</#list>
	</#if>
	
	 public ${entity.name?cap_first} builder() {
        return pojoEntity;
     }
 }

	@Override 
	public String toString(){ 
		return ToStringBuilder.reflectionToString(this); 
	}
}


