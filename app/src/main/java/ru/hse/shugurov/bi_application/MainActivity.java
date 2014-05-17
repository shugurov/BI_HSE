package ru.hse.shugurov.bi_application;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import ru.hse.shugurov.bi_application.gui.NavigationDrawerFragment;
import ru.hse.shugurov.bi_application.gui.adapters.NavigationDrawerAdapter;
import ru.hse.shugurov.bi_application.gui.fragments.BaseFragment;
import ru.hse.shugurov.bi_application.gui.fragments.items.AboutAppFragment;
import ru.hse.shugurov.bi_application.gui.fragments.items.AboutUsFragment;
import ru.hse.shugurov.bi_application.gui.fragments.lists.EventsFragment;
import ru.hse.shugurov.bi_application.gui.fragments.lists.FragmentWithList;
import ru.hse.shugurov.bi_application.gui.fragments.special.BillboardFragment;
import ru.hse.shugurov.bi_application.gui.fragments.special.SchedulePlaceholderFragment;
import ru.hse.shugurov.bi_application.gui.fragments.special.SettingFragment;
import ru.hse.shugurov.bi_application.sections.EventsScreen;
import ru.hse.shugurov.bi_application.sections.MultipleAdaptersViewSection;
import ru.hse.shugurov.bi_application.sections.ReferencesSection;
import ru.hse.shugurov.bi_application.sections.Section;
import ru.hse.shugurov.bi_application.sections.SingleViewSection;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks
{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private String mTitle;
    private Section[] sections;
    private BaseFragment[] fragments;
    private BaseFragment currentPlaceholder;
    private NavigationDrawerAdapter navigationDrawerAdapter;
    private boolean wasRestored;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        wasRestored = savedInstanceState != null;
        FileCache.init(this);
        ImageLoader.init(this);
        ApplicationStructure.setContext(this);
        ApplicationStructure structure = ApplicationStructure.getStructure();
        sections = structure.getSections();
        fragments = new BaseFragment[sections.length]; //TODO зачем создаю массив??(
        for (int i = 0; i < fragments.length; i++)
        {
            fragments[i] = null;
        }
        setContentView(R.layout.activity_main);
        navigationDrawerAdapter = new NavigationDrawerAdapter(this, sections);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = sections[0].getTitle();

        // Set up the drawer.

        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), navigationDrawerAdapter);

    }

    @Override
    public void onNavigationDrawerItemSelected(final int position)
    {
        if (wasRestored)
        {
            wasRestored = false;
            return;
        }
        //navigationDrawerAdapter.checkItem(position);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle arguments = new Bundle();
        arguments.putSerializable(FragmentWithList.SECTION_TAG, sections[position]);
        switch (sections[position].getType())
        {
            case ContentTypes.NEWS:
                if (sections[position] instanceof SingleViewSection)
                {
                    currentPlaceholder = new FragmentWithList();
                }
                break;
            case ContentTypes.PROJECTS_VOLUNTEERING:
                if (sections[position] instanceof SingleViewSection)
                {
                    currentPlaceholder = new FragmentWithList();
                }
                break;
            case ContentTypes.CONTACTS:
                if (sections[position] instanceof SingleViewSection)
                {
                    currentPlaceholder = new FragmentWithList();
                }
                break;
            case ContentTypes.EVENTS:
                if (sections[position] instanceof EventsScreen)
                {
                    currentPlaceholder = new EventsFragment();
                    arguments.putSerializable(EventsFragment.CURRENT_SCREEN_TAG, sections[position]);
                }
                break;
            case ContentTypes.BILLBOARD:
                if (sections[position] instanceof MultipleAdaptersViewSection)
                {
                    currentPlaceholder = new BillboardFragment();
                }
                break;
            case ContentTypes.SCHEDULE:
                if (sections[position] instanceof ReferencesSection)
                {
                    currentPlaceholder = new SchedulePlaceholderFragment();
                }
                break;
            case ContentTypes.TEACHERS:
                if (sections[position] instanceof SingleViewSection)
                {
                    currentPlaceholder = new FragmentWithList();
                }
                break;
            case ContentTypes.SETTINGS:
            {
                currentPlaceholder = new SettingFragment();
                break;
            }
            case ContentTypes.ABOUT_US:
            {
                currentPlaceholder = new AboutUsFragment();
                break;
            }
            case ContentTypes.ABOUT_APP:
                currentPlaceholder = new AboutAppFragment();
                break;
        }

        fragments[position] = currentPlaceholder;
        currentPlaceholder.setArguments(arguments);
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
}
