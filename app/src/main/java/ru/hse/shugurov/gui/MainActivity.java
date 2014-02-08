package ru.hse.shugurov.gui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.Toast;

import ru.hse.shugurov.ApplicationStructure;
import ru.hse.shugurov.ContentTypes;
import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.adapters.NavigationDrawerAdapter;
import ru.hse.shugurov.gui.placeholders.AboutUsPlaceholderFragment;
import ru.hse.shugurov.gui.placeholders.BillboardPlaceholderFragment;
import ru.hse.shugurov.gui.placeholders.EventsPlaceholderFragment;
import ru.hse.shugurov.gui.placeholders.PlaceholderFragment;
import ru.hse.shugurov.gui.placeholders.PlaceholderFragmentWithList;
import ru.hse.shugurov.gui.placeholders.SchedulePlaceholderFragment;
import ru.hse.shugurov.gui.placeholders.SettingPlaceholderFragment;
import ru.hse.shugurov.sections.MultipleAdaptersViewSection;
import ru.hse.shugurov.sections.MultipleViewScreen;
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
        String lol = getResources().getString(R.string.app_name);
        ApplicationStructure.setContext(this);
        ApplicationStructure structure = ApplicationStructure.getStructure();
        sections = structure.getSections();
        fragments = new PlaceholderFragment[sections.length];
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
    public void onBackPressed()
    {
        //TODO обдумать, что делать, если открыт drawer
        if (currentPlaceholder != null)
        {
            if (!currentPlaceholder.moveBack())
            {
                return;
            }
        } else
        {
            Toast.makeText(this, "Нет Интернет соединения", Toast.LENGTH_SHORT).show();
        }
        super.onBackPressed();

    }

    @Override
    public void onNavigationDrawerItemSelected(final int position)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentChanged fragmentChanged = new FragmentChanged()
        {
            @Override
            public void setCurrentFragment(PlaceholderFragment currentFragment)
            {
                MainActivity.this.currentPlaceholder = currentFragment;
            }
        };
        switch (sections[position].getType())
        {
            case ContentTypes.NEWS:
                if (sections[position] instanceof SingleViewSection)
                {
                    currentPlaceholder = new PlaceholderFragmentWithList(this, fragmentChanged, sections[position], position);
                }
                break;
            case ContentTypes.PROJECTS_VOLUNTEERING:
                if (sections[position] instanceof SingleViewSection)
                {
                    currentPlaceholder = new PlaceholderFragmentWithList(this, fragmentChanged, sections[position], position);
                }
                break;
            case ContentTypes.CONTACTS:
                if (sections[position] instanceof SingleViewSection)
                {
                    currentPlaceholder = new PlaceholderFragmentWithList(this, fragmentChanged, sections[position], position);
                }
                break;
            case ContentTypes.EVENTS:
                if (sections[position] instanceof MultipleViewScreen)
                {
                    currentPlaceholder = new EventsPlaceholderFragment(this, fragmentChanged, (MultipleViewScreen) sections[position], position);
                }
                break;
            case ContentTypes.BILLBOARD:
                if (sections[position] instanceof MultipleAdaptersViewSection)
                {
                    currentPlaceholder = new BillboardPlaceholderFragment(this, fragmentChanged, (MultipleAdaptersViewSection) sections[position], position);
                }
                break;
            case ContentTypes.SCHEDULE:
                if (sections[position] instanceof ReferencesSection)
                {
                    currentPlaceholder = new SchedulePlaceholderFragment(this, fragmentChanged, sections[position], position);
                }
                break;
            case ContentTypes.TEACHERS:
                if (sections[position] instanceof SingleViewSection)
                {
                    currentPlaceholder = new PlaceholderFragmentWithList(this, fragmentChanged, sections[position], position);
                }
                break;
            case ContentTypes.SETTINGS:
            {
                currentPlaceholder = new SettingPlaceholderFragment(this, fragmentChanged, sections[position], position);
                break;
            }
            case ContentTypes.ABOUT_US:
            {
                currentPlaceholder = new AboutUsPlaceholderFragment(this, fragmentChanged, sections[position], position);
                break;
            }
            default:
                currentPlaceholder = new PlaceholderFragment(this, fragmentChanged, sections[position], position);
                break;
        }

        fragments[position] = currentPlaceholder;
        fragmentManager.beginTransaction().replace(R.id.container, currentPlaceholder).commit();
    }

    public void onSectionAttached(int number)
    {
        mTitle = sections[number].getTitle();
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

    public interface FragmentChanged
    {
        void setCurrentFragment(PlaceholderFragment currentFragment);
    }
}
