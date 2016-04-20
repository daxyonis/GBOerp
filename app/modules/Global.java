package modules;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import javax.inject.*;

import play.db.DBApi;
import play.Configuration;
import play.cache.CacheApi;
import play.inject.ApplicationLifecycle;
import play.libs.F;

import configs.AppConfig;
import configs.DataConfig;

@Singleton
public class Global {

    private AnnotationConfigApplicationContext ctx;
	
	@Inject	
    public Global(DBApi databaseAPI, 
				  CacheApi cacheApi,
    			  Configuration configuration,
    			  ApplicationLifecycle lifecycle) {
		
        ctx = new AnnotationConfigApplicationContext();		
		ctx.getBeanFactory().registerSingleton("dataSource", databaseAPI.getDatabase("default").getDataSource());	
		ctx.getBeanFactory().registerSingleton("configuration", configuration);
		ctx.getBeanFactory().registerSingleton("cacheApi", cacheApi);
		ctx.register(DataConfig.class, AppConfig.class);
		ctx.refresh(); 
		ctx.registerShutdownHook();
		
		lifecycle.addStopHook(() -> {
            ctx.close();
            return F.Promise.pure(null);
        });
				
    }

	public <A> A getBean(Class<A> clazz) {
	        return ctx.getBean(clazz);
    }

}