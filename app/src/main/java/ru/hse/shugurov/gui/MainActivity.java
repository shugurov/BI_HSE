package ru.hse.shugurov.gui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import java.io.Serializable;

import ru.hse.shugurov.ApplicationStructure;
import ru.hse.shugurov.ContentTypes;
import ru.hse.shugurov.FileCache;
import ru.hse.shugurov.ImageLoader;
import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.adapters.NavigationDrawerAdapter;
import ru.hse.shugurov.gui.placeholders.PlaceholderFragment;
import ru.hse.shugurov.gui.placeholders.items.AboutAppPlaceholder;
import ru.hse.shugurov.gui.placeholders.items.AboutUsPlaceholderFragment;
import ru.hse.shugurov.gui.placeholders.lists.EventsPlaceholderFragment;
import ru.hse.shugurov.gui.placeholders.lists.PlaceholderFragmentWithList;
import ru.hse.shugurov.gui.placeholders.special.BillboardPlaceholderFragment;
import ru.hse.shugurov.gui.placeholders.special.SchedulePlaceholderFragment;
import ru.hse.shugurov.gui.placeholders.special.SettingPlaceholderFragment;
import ru.hse.shugurov.sections.EventsScreen;
import ru.hse.shugurov.sections.MultipleAdaptersViewSection;
import ru.hse.shugurov.sections.ReferencesSection;
import ru.hse.shugurov.sections.Section;
import ru.hse.shugurov.sections.SingleViewSection;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks
{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private Section[] sections;
    private PlaceholderFragment[] fragments;
    private PlaceholderFragment currentPlaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //this.getSupportFragmentManager().findFragmentById()  //TODO что делает byTag?
        FileCache.init(this);
        ImageLoader.init(this);
        ApplicationStructure.setContext(this);
        ApplicationStructure structure = ApplicationStructure.getStructure();
        sections = structure.getSections();
        fragments = new PlaceholderFragment[sections.length]; //TODO зачем создаю массив??(
        for (int i = 0; i < fragments.length; i++)
        {
            fragments[i] = null;
        }
        setContentView(R.layout.activity_main);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = sections[0].getTitle();

        // Set up the drawer.
        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(this, sections);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), adapter);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        Bundle fragmentArguments = currentPlaceholder.getArguments();
        outState.putAll(fragmentArguments);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onBackPressed()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();

    }

    @Override
    public void onNavigationDrawerItemSelected(final int position)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentListener fragmentListener = new FragmentListener()
        {
            @Override
            public void setCurrentFragment(PlaceholderFragment currentFragment)
            {
                MainActivity.this.currentPlaceholder = currentFragment;
            }

            @Override
            public void setSectionTitle(String title)
            {
                mTitle = title;
                restoreActionBar();
            }
        };
        switch (sections[position].getType())
        {
            case ContentTypes.NEWS:
                if (sections[position] instanceof SingleViewSection)
                {
                    currentPlaceholder = new PlaceholderFragmentWithList(fragmentListener, sections[position]);
                }
                break;
            case ContentTypes.PROJECTS_VOLUNTEERING:
                if (sections[position] instanceof SingleViewSection)
                {
                    currentPlaceholder = new PlaceholderFragmentWithList(fragmentListener, sections[position]);
                }
                break;
            case ContentTypes.CONTACTS:
                if (sections[position] instanceof SingleViewSection)
                {
                    currentPlaceholder = new PlaceholderFragmentWithList(fragmentListener, sections[position]);
                }
                break;
            case ContentTypes.EVENTS:
                if (sections[position] instanceof EventsScreen)
                {
                    currentPlaceholder = new EventsPlaceholderFragment(fragmentListener, (EventsScreen) sections[position]);
                }
                break;
            case ContentTypes.BILLBOARD:
                if (sections[position] instanceof MultipleAdaptersViewSection)
                {
                    currentPlaceholder = new BillboardPlaceholderFragment(fragmentListener, (MultipleAdaptersViewSection) sections[position]);
                }
                break;
            case ContentTypes.SCHEDULE:
                if (sections[position] instanceof ReferencesSection)
                {
                    currentPlaceholder = new SchedulePlaceholderFragment(fragmentListener, sections[position]);
                }
                break;
            case ContentTypes.TEACHERS:
                if (sections[position] instanceof SingleViewSection)
                {
                    currentPlaceholder = new PlaceholderFragmentWithList(fragmentListener, sections[position]);
                }
                break;
            case ContentTypes.SETTINGS:
            {
                currentPlaceholder = new SettingPlaceholderFragment(fragmentListener, sections[position]);
                break;
            }
            case ContentTypes.ABOUT_US:
            {
                currentPlaceholder = new AboutUsPlaceholderFragment(fragmentListener, sections[position]);
                break;
            }
            case ContentTypes.ABOUT_APP:
                currentPlaceholder = new AboutAppPlaceholder(fragmentListener, sections[position]);
                break;
        }

        fragments[position] = currentPlaceholder;
        fragmentManager.beginTransaction().replace(R.id.container, currentPlaceholder).commit();
    }


    public void restoreActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        if (!mNavigationDrawerFragment.isDrawerOpen())
        {
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    public abstract class FragmentListener implements Serializable
    {
        public abstract void setCurrentFragment(PlaceholderFragment currentFragment);

        public abstract void setSectionTitle(String title);
    }
}
